package edu.brown.cs.student.weekli.main;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.weekli.schedule.Block;
import edu.brown.cs.student.weekli.schedule.Scheduler;
import edu.brown.cs.student.weekli.schedule.Task;
import edu.brown.cs.student.weekli.user.Database;
import edu.brown.cs.student.weekli.user.User;
import spark.*;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handlers {

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
            QueryParamsMap qm = request.queryMap();
            String username = qm.value("username");
            String password = qm.value("password");
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
}
