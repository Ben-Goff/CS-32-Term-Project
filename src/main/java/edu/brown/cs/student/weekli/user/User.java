package edu.brown.cs.student.weekli.user;


import edu.brown.cs.student.weekli.schedule.Block;
import edu.brown.cs.student.weekli.schedule.Commitment;
import edu.brown.cs.student.weekli.schedule.Project;
import edu.brown.cs.student.weekli.schedule.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class User {

    private String iD;
    private String password;
    private List<Commitment> commitments;
    private List<Task> tasks;
    private List<Project> projects;
    private List<Block> schedule;

    public User(String iD, String pw) throws ClassNotFoundException, SQLException {
        this.schedule = new ArrayList<>();
        this.iD = iD;
        this.password = pw;
        this.commitments = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.projects = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/users.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                + "id TEXT,"
                + "password TEXT);");
        prep.executeUpdate();
        urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
        conn = DriverManager.getConnection(urlToDB);
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS tasks ("
                + "id TEXT,"
                + "user TEXT,"
                + "startTime INTEGER,"
                + "endTime INTEGER,"
                + "estTime INTEGER,"
                + "name TEXT,"
                + "description TEXT,"
                + "progress FLOAT,"
                + "session INTEGER,"
                + "project TEXT,"
                + "PRIMARY KEY (id));");
        prep.executeUpdate();
        urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
        conn = DriverManager.getConnection(urlToDB);
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS commitments ("
                + "id TEXT,"
                + "user TEXT,"
                + "startTime INTEGER,"
                + "endTime INTEGER,"
                + "estTime INTEGER,"
                + "name TEXT,"
                + "description TEXT,"
                + "repeating INTEGER"
                + "PRIMARY KEY (id));");
        prep.executeUpdate();
        urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
        conn = DriverManager.getConnection(urlToDB);
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS projects ("
                + "id TEXT,"
                + "user TEXT,"
                + "name TEXT,"
                + "description TEXT,"
                + "PRIMARY KEY (id));");
        prep.executeUpdate();
        urlToDB = "jdbc:sqlite:data/weekli/schedules.sqlite3";
        conn = DriverManager.getConnection(urlToDB);
        prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS schedules ("
                + "user TEXT"
                + "id TEXT"
                + "startTime INTEGER"
                + "endTime INTEGER;");
        prep.executeUpdate();
        prep.close();
    }

    public void addTask(Task add) throws ClassNotFoundException, SQLException {
        this.tasks.add(add);
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("INSERT INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, session, project)"
                + "VALUES ("+add.getID().toString()+", "+ this.iD +", "+add.getStartDate()+", "+add.getEndDate()+", "+add.getEstimatedTime()+", "+add.getName()+", "+add.getDescription()+", "+add.getProgress()+", "+add.getSessionTime()+", "+add.getProjectID().toString()+");");
        prep.executeUpdate();
        prep.close();
    }

    public void addTask(long start, long end, long estTime, String name, String description, long sessionTime) throws ClassNotFoundException, SQLException {
        Task add = new Task(start, end, estTime, name, description, sessionTime);
        this.tasks.add(add);
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("INSERT INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, session, project)"
                + "VALUES ("+add.getID().toString()+", "+ this.iD +", "+add.getStartDate()+", "+add.getEndDate()+", "+add.getEstimatedTime()+", "+add.getName()+", "+add.getDescription()+", "+add.getProgress()+", "+add.getSessionTime()+", "+add.getProjectID().toString()+");");
        prep.executeUpdate();
        prep.close();
    }

    public void addCommitment(long start, long end, String name, String description, Optional<Long> repeating) throws ClassNotFoundException, SQLException {
        Commitment add = new Commitment(start, end, name, description, repeating);
        this.commitments.add(add);
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);

        Optional<Long> rep = add.getRepeating();
        PreparedStatement prep;
        if (rep.isPresent()) {
            prep = conn.prepareStatement("INSERT INTO commitments (id, user, startTime, endTime, estTime, name, description, repeating)"
                    + "VALUES (" + add.getID().toString() + ", " + this.iD + ", " + add.getStartDate() + ", " + add.getEndDate() + ", " + add.getEstimatedTime() + ", " + add.getName() + ", " + add.getDescription() + ",  " + add.getRepeating().get() +");");
        } else {
            prep = conn.prepareStatement("INSERT INTO commitments (id, user, startTime, endTime, estTime, name, description)"
                    + "VALUES (" + add.getID().toString() + ", " + this.iD + ", " + add.getStartDate() + ", " + add.getEndDate() + ", " + add.getEstimatedTime() + ", " + add.getName() + ", " + add.getDescription() +");");
        }
        prep.executeUpdate();
        prep.close();
    }

    public void addProject(String name, String description, List<UUID> checkpoints) throws ClassNotFoundException, SQLException {
        Project add = new Project(name, description);
        this.tasks.stream().filter(t -> checkpoints.contains(t.getID())).forEach(task -> {
            task.addProjectID(add.getiD());
            try {
                addTask(task);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("INSERT INTO projects (id, user, name, description)"
                + "VALUES ("+add.getiD().toString()+", "+ this.iD +", "+add.getName()+", "+add.getDescription()+");");
        prep.executeUpdate();
        prep.close();
        this.projects.add(add);
    }

    public void storeSchedule() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/schedules.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = null;
        for (Block b: schedule) {
            prep = conn.prepareStatement("INSERT INTO schedules (user, id, startTime, endTime)"
                    + "VALUES ("+this.iD+","+b.getiD().toString()+","+b.getStartTime()+","+b.getEndTime()+");");
            prep.executeUpdate();
        }
        if (prep != null){
            prep.close();
        }
    }
}
