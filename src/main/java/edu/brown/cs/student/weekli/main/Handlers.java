package edu.brown.cs.student.weekli.main;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.weekli.schedule.Block;
import edu.brown.cs.student.weekli.schedule.Scheduler;
import edu.brown.cs.student.weekli.schedule.Task;
import edu.brown.cs.student.weekli.user.Database;
import edu.brown.cs.student.weekli.user.User;
import spark.*;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Handlers {

  // CreateCommitment Send Order:
  //  1) name
  //  2) description
  //  3) startTime -- explicitly given
  //  4) endTime -- Calculated in front based on duration (not explicit)
  //  5) periodOfRepitition -- if non repeating, empty string
  //
  // CreateTask Send Order:
  //  1) name -- (this is Title)
  //  2) description
  //  3) startTime -- time of creation
  //  4) endTime -- combo of date and time
  //  5) estTime -- this is estimated effort in milliseconds
  //  6) sessionTime -- in milliseconds
  //  7) color
  //
  // CreateProject Send Order:
  //  1) name
  //  2) description
  //  3) checkpoints -- 2D String array (inner arrays are all Strings arrays with same order as normal task)
  //
  // UpdateProgress Send Order:
  //  1) id -- id of task/checkpoint to be updated (can be taken from block or list of tasks)
  //  2) progress -- new progress
  //  *** If both fields are empty, we will assume an auto update
  //
  // GetSchedule Send Order:
  //  1) start -- the start time of the desired schedule blocks
  //  2) end -- the ens time of the desired schedule blocks
  ///

    private static Database db;

  static {
    try {
      db = new Database();
    } catch (SQLException | ClassNotFoundException throwables) {
      throwables.printStackTrace();
    }
  }

  private static final Gson GSON = new Gson();

    /**
     * Handles requests made for a route.
     */

    protected static class LoginHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String username = data.getString("username");
            String password = data.getString("password");
            Map<String, Object> variables;
            String message = "";
            System.out.println("b4");
            User loggingIn = db.signIn(username, password); //TODO: NOTHING RUNS AFTER THIS WHEN USER IS NOT WORKING
            System.out.println("after");
            if(loggingIn == null) {
                message = "login failed";
                variables = ImmutableMap.of("message", message);
                return GSON.toJson(variables);
            }
            loggingIn.loadCommitments();
            loggingIn.loadTasks();
            loggingIn.loadProjects();
            loggingIn.loadSchedule();
            HttpSession session = request.session().raw();
            session.setAttribute("user", loggingIn);
            List<String> complete = loggingIn.updateSchedule().stream().map(t -> t.getID().toString()).collect(Collectors.toList());
            message = "login successful";
            variables = ImmutableMap.of("message", message, "complete", complete);
            return GSON.toJson(variables);
        }
    }

    protected static class SignUpHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            QueryParamsMap qm = request.queryMap();
            String username = qm.value("username");
            String password = qm.value("password");
            Map<String, Object> variables;
            String message = "";
            User loggingIn = db.signUp(username, password);
            if(loggingIn == null) {
                message = "user ID already exists";
                variables = ImmutableMap.of("message", message);
                return GSON.toJson(variables);
            }
            HttpSession session = request.session().raw();
            session.setAttribute("user", loggingIn);
            message = "sign up successful";
            variables = ImmutableMap.of("message", message);
            return GSON.toJson(variables);
        }
    }

    protected static class GetScheduleHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            Map<String, Object> variables;
            QueryParamsMap qm = request.queryMap();
            String start = qm.value("start");
            String end = qm.value("end");
            HttpSession session = request.session().raw();
            User loggedIn = (User) session.getAttribute("user");
            Scheduler s = new Scheduler(loggedIn.getCommitments());
            List<Block> blocks = s.schedule(loggedIn.getTasks(), Long.parseLong(start), Long.parseLong(end));
            List<List<Block>> toReturn = new ArrayList<>();
            toReturn.add(loggedIn.getPastBlocks().stream().filter(b -> b.getEndTime() >= Long.parseLong(start) && b.getStartTime() <= Long.parseLong(end)).collect(Collectors.toList()));
            toReturn.add(blocks.stream().filter(b -> b.getEndTime() >= Long.parseLong(start) && b.getStartTime() <= Long.parseLong(end)).collect(Collectors.toList()));
            List<String[]> schedule = toReturn.stream().flatMap(Collection::stream).map(b -> new String[]{""+b.getStartTime(), ""+b.getEndTime(), b.getiD().toString()}).collect(Collectors.toList());
            variables = ImmutableMap.of("schedule", schedule);
            return GSON.toJson(variables);
        }
    }

    private static class UpdateProgressHandler implements Route {
      public Object handle(Request request, Response response) throws Exception {
        QueryParamsMap qm = request.queryMap();
        String id = qm.value("id");
        String progress = qm.value("progress");
        Map<String, Object> variables;
        List<String[]> complete;
        List<String[]> tasks;
        HttpSession session = request.session().raw();
        User loggedIn = (User) session.getAttribute("user");
        if (id.equals("") && progress.equals("")) {
          List<Task> c = loggedIn.updateSchedule();
          complete = c.stream().map(t -> new String[]{t.getName(), t.getDescription(), t.getID().toString(), Long.toString(t.getProgress())}).collect(Collectors.toList());
          tasks = loggedIn.getTasks().stream().map(t -> new String[]{t.getName(), t.getDescription(), t.getID().toString(), Long.toString(t.getProgress())}).collect(Collectors.toList());
          // send back both completed tasks as well as all tasks
          variables = ImmutableMap.of("complete", complete, "tasks", tasks);
        } else {
          UUID ID = UUID.fromString(id);
          long prog = Long.parseLong(progress);
          Task toUpdate = loggedIn.belongsToTask(ID);
          toUpdate.setProgress(prog);
          loggedIn.updateTaskInDB(toUpdate);
          String[] task = new String[]{toUpdate.getName(), toUpdate.getDescription(), toUpdate.getID().toString(), Long.toString(toUpdate.getProgress())};
          // send back updated task
          variables = ImmutableMap.of("updated", task);
        }
        return GSON.toJson(variables);
      }
    }

  private static class CreateTaskHandler implements Route {
    public Object handle(Request request, Response response) throws Exception {
      QueryParamsMap qm = request.queryMap();
      String name = qm.value("name");
      String description = qm.value("description");
      long startTime = Long.parseLong(qm.value("startTime"));
      long endTime = Long.parseLong(qm.value("endTime"));
      long estTime = Long.parseLong(qm.value("estTime"));
      long sessionTime = Long.parseLong(qm.value("sessionTime"));
      Map<String, Object> variables = ImmutableMap.of();
      HttpSession session = request.session().raw();
      User loggedIn = (User) session.getAttribute("user");
      loggedIn.addTask(startTime, endTime, estTime, name, description, sessionTime);

      //  1) name -- (this is Title)
      //  2) description
      //  3) startTime -- time of creation
      //  4) endTime -- combo of date and time
      //  5) estTime -- this is estimated effort in milliseconds
      //  6) sessionTime -- in milliseconds

      return GSON.toJson(variables);
    }
  }

    private static class CreateCommitmentHandler implements Route {
      public Object handle(Request request, Response response) throws Exception {
        QueryParamsMap qm = request.queryMap();
        String name = qm.value("name");
        String description = qm.value("description");
        long startTime = Long.parseLong(qm.value("startTime"));
        long endTime = Long.parseLong(qm.value("endTime"));
        String periodOfRepitition = qm.value("periodOfRepitition");
        Map<String, Object> variables = ImmutableMap.of();
        HttpSession session = request.session().raw();
        User loggedIn = (User) session.getAttribute("user");
        if (!periodOfRepitition.equals("")) {
          loggedIn.addCommitment(startTime, endTime, name, description, Optional.of(Long.parseLong(periodOfRepitition)));
        } else {
          loggedIn.addCommitment(startTime, endTime, name, description, Optional.empty());
        }
        //  1) name
        //  2) description
        //  3) startTime -- explicitly given
        //  4) endTime -- Calculated in front based on duration (not explicit)
        //  5) periodOfRepitition -- if non repeating, empty string

        return GSON.toJson(variables);
      }
  }

    private static class DeleteTaskHandler implements Route {
      public Object handle(Request request, Response response) throws Exception {
        QueryParamsMap qm = request.queryMap();
        String id = qm.value("id");
        Map<String, Object> variables = ImmutableMap.of();
        HttpSession session = request.session().raw();
        User loggedIn = (User) session.getAttribute("user");
        loggedIn.deleteTask(id);

        //  1) name -- (this is Title)
        //  2) description
        //  3) startTime -- time of creation
        //  4) endTime -- combo of date and time
        //  5) estTime -- this is estimated effort in milliseconds
        //  6) sessionTime -- in milliseconds

        return GSON.toJson(variables);
      }
    }

      private static class DeleteCommitmentHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
          QueryParamsMap qm = request.queryMap();
          String id = qm.value("id");
          Map<String, Object> variables = ImmutableMap.of();
          HttpSession session = request.session().raw();
          User loggedIn = (User) session.getAttribute("user");
          loggedIn.deleteCommitment(id);

          //  1) name
          //  2) description
          //  3) startTime -- explicitly given
          //  4) endTime -- Calculated in front based on duration (not explicit)
          //  5) periodOfRepitition -- if non repeating, empty string

          return GSON.toJson(variables);
        }
      }


//    //TODO: - 3 Create Handlers
//            - 3 Delete Handlers
//            - 3 Get Handelrs -- decided that we only need to send Tasks for now so just make request
  //            to getProgress with empty params
//
//            - (Update Progress) -- Done
}
