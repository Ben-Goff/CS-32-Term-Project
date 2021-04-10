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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
  ///

    private static Database db;
    private static final Gson GSON = new Gson();

    public Handlers() throws SQLException, ClassNotFoundException {
        db = new Database();
    }

    /**
     * Handles requests made for a route.
     */

    protected static class LoginHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String username = data.getString("username");
            String password = data.getString("password");
            System.out.println(username + password);
            Map<String, Object> variables;
            String message = "";
            User loggingIn = db.signIn(username, password);
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
            loggingIn.loadSchedule();
            List<String> complete = loggingIn.updateSchedule().stream().map(t -> t.getID().toString()).collect(Collectors.toList());
            List<String[]> schedule = loggingIn.getSchedule().stream().map(b -> new String[]{""+b.getStartTime(), ""+b.getEndTime(), b.getiD().toString()}).collect(Collectors.toList());
            message = "login successful";
            variables = ImmutableMap.of("message", message, "tasks complete", complete, "schedule", schedule);
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
            String message = "";
            HttpSession session = request.session().raw();
            User loggedIn = (User) session.getAttribute("user");
            Scheduler s = new Scheduler(loggedIn.getCommitments());
            List<Block> blocks = s.schedule(loggedIn.getTasks());
            List<String[]> schedule = blocks.stream().map(b -> new String[]{""+b.getStartTime(), ""+b.getEndTime(), b.getiD().toString()}).collect(Collectors.toList());
            message = "login successful";
            variables = ImmutableMap.of("message", message, "schedule", schedule);
            return GSON.toJson(variables);
        }
    }


//    //TODO: - 3 Create Handlers
//            - 3 Delete Handlers
//            - 3 Get Handelrs
//            - Update Progress
}
