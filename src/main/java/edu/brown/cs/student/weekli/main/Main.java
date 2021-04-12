package edu.brown.cs.student.weekli.main;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.weekli.schedule.Block;
import edu.brown.cs.student.weekli.schedule.Scheduler;
import edu.brown.cs.student.weekli.schedule.Task;
import edu.brown.cs.student.weekli.user.Database;
import edu.brown.cs.student.weekli.user.User;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.json.JSONObject;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final int DEFAULT_PORT = 4567;
    private static Database db;
    private static User current;

    static {
        try {
            db = new Database();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private static final Gson GSON = new Gson();


  /**
     * The initial method called when execution begins.
     *
     * @param args An array of command line arguments
     */
    public static void main(String[] args) {
        new Main(args).run();
    }

    private String[] args;

    private Main(String[] args) {
        this.args = args;
    }

    private void run() {

        // Parse command line arguments
        OptionParser parser = new OptionParser();
        parser.accepts("gui");
        parser.accepts("port").withRequiredArg().ofType(Integer.class)
                .defaultsTo(DEFAULT_PORT);
        OptionSet options = parser.parse(args);

        if (options.has("gui")) {
            runSparkServer((int) options.valueOf("port"));
        }
    }

    private static FreeMarkerEngine createEngine() {
        Configuration config = new Configuration();
        File templates = new File("src/main/resources/spark/template/freemarker");
        try {
            config.setDirectoryForTemplateLoading(templates);
        } catch (IOException ioe) {
            System.out.printf("ERROR: Unable use %s for template loading.%n",
                    templates);
            System.exit(1);
        }
        return new FreeMarkerEngine(config);
    }

    private void runSparkServer(int port) {
        Spark.port(port);
        Spark.externalStaticFileLocation("src/main/resources/static");
        Spark.exception(Exception.class, new ExceptionPrinter());
        FreeMarkerEngine freeMarker = createEngine();
        Spark.options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        // Setup Spark Routes
        Spark.post("/login", new LoginHandler());
        Spark.post("/signup", new SignUpHandler());
        Spark.post("/schedule", new GetScheduleHandler());
        Spark.post("/update", new UpdateProgressHandler());
        Spark.post("/createcommitment", new CreateCommitmentHandler());
        Spark.post("/createtask", new CreateTaskHandler());
        Spark.post("/deletecommitment", new DeleteCommitmentHandler());
        Spark.post("/deletetask", new DeleteTaskHandler());
    }


    /**
     * Display an error page when an exception occurs in the server.
     */
    private static class ExceptionPrinter implements ExceptionHandler {
        @Override
        public void handle(Exception e, Request req, Response res) {
            res.status(500);
            StringWriter stacktrace = new StringWriter();
            try (PrintWriter pw = new PrintWriter(stacktrace)) {
                pw.println("<pre>");
                e.printStackTrace(pw);
                pw.println("</pre>");
            }
            res.body(stacktrace.toString());
        }
    }

    protected static class LoginHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String username = data.getString("username");
            String password = data.getString("password");
            Map<String, Object> variables;
            String message = "";
            System.out.println(username + "   " + password);
            current = db.signIn(username, password);
            System.out.println("current: " + current);
            if (current == null) {
                message = "login failed";
                variables = ImmutableMap.of("message", message);
                return GSON.toJson(variables);
            }
            current.loadCommitments();
            current.loadTasks();
            current.loadProjects();
            current.loadSchedule();
//            HttpSession session = request.session(false).raw();
//            session.setAttribute("user", current);
            List<String> complete = current.updateSchedule().stream().map(t -> t.getID().toString()).collect(Collectors.toList());
            message = "login successful";
            variables = ImmutableMap.of("message", message, "complete", complete);
            return GSON.toJson(variables);
        }
    }

    protected static class SignUpHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String username = data.getString("username");
            String password = data.getString("password");
            Map<String, Object> variables;
            String message = "";
            User current = db.signUp(username, password);
            if (current == null) {
                message = "user ID already exists";
                variables = ImmutableMap.of("message", message);
                return GSON.toJson(variables);
            }
            HttpSession session = request.session().raw();
            session.setAttribute("user", current);
            message = "sign up successful";
            variables = ImmutableMap.of("message", message);
            return GSON.toJson(variables);
        }
    }

    protected static class GetScheduleHandler implements Route {
        @Override
        public Object handle(Request request, Response response) throws Exception {
            Map<String, Object> variables;
            JSONObject data = new JSONObject(request.body());

            long start = data.getLong("start");
            long end = data.getLong("end");

            HttpSession session = request.session().raw();
            System.out.println("0: " + session);
            System.out.println("1: " + current);
            Scheduler s = new Scheduler(current.getCommitments());
            System.out.println("1.5");
            List<Block> blocks = s.schedule(current.getTasks(), start, end);
            List<List<Block>> allBlocks = Arrays.asList(blocks, current.getPastBlocks());
            List<Block> blocksToReturn = allBlocks.stream().flatMap(Collection::stream).map(b -> {
                List<Block> blockBlocks = new ArrayList<>();
                Date startDate = (new Date(b.getStartTime()));
                Date endDate = (new Date(b.getEndTime()));
                if ((new Date(startDate.getTime() + startDate.getTimezoneOffset())).getDay() != (new Date(endDate.getTime() + endDate.getTimezoneOffset())).getDay()) {
                    System.out.println("hellooooooo" + (b.getEndTime() - (b.getEndTime() % 86400000) - 1 + startDate.getTimezoneOffset()* 60000L));
                    blockBlocks.add(new Block(b.getStartTime(), b.getEndTime() - (b.getEndTime() % 86400000) - 1 + startDate.getTimezoneOffset()* 60000L, b.getiD(), b.getName(), b.getDescription(), b.getColor()));
                    blockBlocks.add(new Block(b.getEndTime() - (b.getEndTime() % 86400000) + endDate.getTimezoneOffset()* 60000L, b.getEndTime(), b.getiD(), b.getName(), b.getDescription(), b.getColor()));
                    return blockBlocks;
                } else {
                    return Collections.singletonList(b);
                }
            }).flatMap(Collection::stream).collect(Collectors.toList());
            System.out.println("2");
            List<List<Block>> toReturn = new ArrayList<>();
            toReturn.add(blocksToReturn.stream().filter(b -> b.getEndTime() >= start && b.getStartTime() <= end).collect(Collectors.toList()));
            System.out.println("3");
            List<String[]> schedule = toReturn.stream().flatMap(Collection::stream).map(b -> new String[]{"" + b.getStartTime(), "" + b.getEndTime(), b.getiD().toString(), b.getName(), b.getDescription(), b.getColor()}).collect(Collectors.toList());
            variables = ImmutableMap.of("schedule", schedule);
            System.out.println("4");
            return GSON.toJson(variables);
        }
    }

    protected static class UpdateProgressHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String id = data.getString("id");
            String progress = data.getString("progress");
            Map<String, Object> variables;
            List<String[]> complete;
            List<String[]> tasks;
            // HttpSession session = request.session().raw();
            if (id.equals("") && progress.equals("")) {
                List<Task> c = current.updateSchedule();
                complete = c.stream().map(t -> new String[]{t.getName(), t.getDescription(), t.getID().toString(), Long.toString(t.getProgress()), t.getColor()}).collect(Collectors.toList());
                tasks = current.getTasks().stream().map(t -> new String[]{t.getName(), t.getDescription(), t.getID().toString(), Long.toString(t.getProgress()), t.getColor()}).collect(Collectors.toList());
                // send back both completed tasks as well as all tasks
                variables = ImmutableMap.of("complete", complete, "tasks", tasks);
            } else {
                UUID ID = UUID.fromString(id);
                long prog = Long.parseLong(progress);
                Task toUpdate = current.belongsToTask(ID);
                toUpdate.setProgress(prog);
                current.updateTaskInDB(toUpdate);
                String[] task = new String[]{toUpdate.getName(), toUpdate.getDescription(), toUpdate.getID().toString(), Long.toString(toUpdate.getProgress()), toUpdate.getColor()};
                // send back updated task
                variables = ImmutableMap.of("updated", task);
            }
            return GSON.toJson(variables);
        }
    }

    protected static class CreateTaskHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String name = data.getString("name");
            String description = data.getString("description");
            long startTime = Long.parseLong(data.getString("startTime"));
            long endTime = Long.parseLong(data.getString("endTime"));
            long estTime = Long.parseLong(data.getString("estTime"));
            long sessionTime = Long.parseLong(data.getString("sessionTime"));
            String color = data.getString("color");
            Map<String, Object> variables = ImmutableMap.of();
            HttpSession session = request.session().raw();
            current.addTask(startTime, endTime, estTime, name, description, sessionTime, color, "");

            //  1) name -- (this is Title)
            //  2) description
            //  3) startTime -- time of creation
            //  4) endTime -- combo of date and time
            //  5) estTime -- this is estimated effort in milliseconds
            //  6) sessionTime -- in milliseconds

            return GSON.toJson(variables);
        }
    }

    protected static class CreateCommitmentHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String name = data.getString("name");
            String description = data.getString("description");
            long startTime = Long.parseLong(data.getString("startTime"));
            long endTime = Long.parseLong(data.getString("endTime"));
            String periodOfRepitition = data.getString("periodOfRepitition");
            Map<String, Object> variables = ImmutableMap.of();
            // HttpSession session = request.session().raw();
            if (!periodOfRepitition.equals("")) {
                current.addCommitment(startTime, endTime, name, description, Optional.of(Long.parseLong(periodOfRepitition)));
            } else {
                System.out.println("Going to run addCommitment");
                System.out.println(current);
                current.addCommitment(startTime, endTime, name, description, Optional.empty());
            }

            //  1) name
            //  2) description
            //  3) startTime -- explicitly given
            //  4) endTime -- Calculated in front based on duration (not explicit)
            //  5) periodOfRepitition -- if non repeating, empty string

            return GSON.toJson(variables);
        }
    }

    protected static class DeleteTaskHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String id = data.getString("id");
            Map<String, Object> variables = ImmutableMap.of();
            HttpSession session = request.session().raw();
            current.deleteTask(id);

            //  1) name -- (this is Title)
            //  2) description
            //  3) startTime -- time of creation
            //  4) endTime -- combo of date and time
            //  5) estTime -- this is estimated effort in milliseconds
            //  6) sessionTime -- in milliseconds

            return GSON.toJson(variables);
        }
    }

    protected static class DeleteCommitmentHandler implements Route {
        public Object handle(Request request, Response response) throws Exception {
            JSONObject data = new JSONObject(request.body());
            String id = data.getString("id");
            Map<String, Object> variables = ImmutableMap.of();
            // HttpSession session = request.session().raw();
            current.deleteCommitment(id);

            //  1) name
            //  2) description
            //  3) startTime -- explicitly given
            //  4) endTime -- Calculated in front based on duration (not explicit)
            //  5) periodOfRepitition -- if non repeating, empty string

            return GSON.toJson(variables);
        }
    }
}