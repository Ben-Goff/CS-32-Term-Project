package edu.brown.cs.student.weekli.user;


import edu.brown.cs.student.weekli.schedule.Block;
import edu.brown.cs.student.weekli.schedule.Commitment;
import edu.brown.cs.student.weekli.schedule.Project;
import edu.brown.cs.student.weekli.schedule.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;
import java.util.stream.Collectors;

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
                + "sessionTime INTEGER,"
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

    public void updateTaskInDB(Task update) throws ClassNotFoundException, SQLException {
//        this.tasks.add(add);
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("INSERT INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, sessionTime, project)"
                + "VALUES ("+update.getID().toString()+", "+ this.iD +", "+update.getStartDate()+", "+update.getEndDate()+", "+update.getEstimatedTime()+", "+update.getName()+", "+update.getDescription()+", "+update.getProgress()+", "+update.getSessionTime()+", "+update.getProjectID().toString()+");");
        prep.executeUpdate();
        prep.close();
    }

    public void addTask(long start, long end, long estTime, String name, String description, long sessionTime) throws ClassNotFoundException, SQLException {
        Task add = new Task(start, end, estTime, name, description, sessionTime);
        this.tasks.add(add);
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
        Connection conn = DriverManager.getConnection(urlToDB);
        PreparedStatement prep = conn.prepareStatement("INSERT INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, sessionTime, project)"
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
            prep = conn.prepareStatement("INSERT INTO commitments (id, user, startTime, endTime, name, description, repeating)"
                    + "VALUES (" + add.getID().toString() + ", " + this.iD + ", " + add.getStartDate() + ", " + add.getEndDate() + ", " + add.getName() + ", " + add.getDescription() + ",  " + add.getRepeating().get() +");");
        } else {
            prep = conn.prepareStatement("INSERT INTO commitments (id, user, startTime, endTime, name, description)"
                    + "VALUES (" + add.getID().toString() + ", " + this.iD + ", " + add.getStartDate() + ", " + add.getEndDate() + ", " + add.getName() + ", " + add.getDescription() +");");
        }
        prep.executeUpdate();
        prep.close();
    }

    public void addProject(String name, String description, List<UUID> checkpoints) throws ClassNotFoundException, SQLException {
        Project add = new Project(name, description);
        this.tasks.stream().filter(t -> checkpoints.contains(t.getID())).forEach(task -> {
            task.addProjectID(add.getiD());
            try {
                updateTaskInDB(task);
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

    public void loadTasks() throws ClassNotFoundException, SQLException {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
      Connection conn = DriverManager.getConnection(urlToDB);
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM tasks WHERE tasks.user = " + this.iD);
      ResultSet rs = prep.executeQuery();
      tasks.clear();
      while (rs.next()) {
        UUID id = UUID.fromString(rs.getString(1));
        long startTime = rs.getLong(3);
        long endTime = rs.getLong(4);
        long estTime = rs.getLong(5);
        String name = rs.getString(6);
        String description = rs.getString(7);
        double progress = rs.getFloat(8);
        long sessionTime = rs.getLong(9);
        UUID project;
        String projString = rs.getString(10);
        if (projString.isEmpty()) {
          project = null;
        } else {
          project = UUID.fromString(projString);
        }
        this.tasks.add(new Task(startTime, endTime, estTime, name, description, progress, id, sessionTime, project));
      }
      prep.close();
      rs.close();
    }

    public void loadCommitments() throws ClassNotFoundException, SQLException {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
      Connection conn = DriverManager.getConnection(urlToDB);
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM commitments WHERE commitments.user = " + this.iD);
      ResultSet rs = prep.executeQuery();
      commitments.clear();
      while (rs.next()) {
        UUID id = UUID.fromString(rs.getString(1));
        long startTime = rs.getLong(3);
        long endTime = rs.getLong(4);
        String name = rs.getString(5);
        String description = rs.getString(6);
        Long rep = (Long) rs.getObject(7);
        Optional<Long> repeating;
        if (rep == null) {
          repeating = Optional.empty();
        } else {
          repeating = Optional.of(rep);
        }
        this.commitments.add(new Commitment(startTime, endTime, name, description, id, repeating));
      }
      prep.close();
      rs.close();
    }

    public void loadProjects() throws ClassNotFoundException, SQLException {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
      Connection conn = DriverManager.getConnection(urlToDB);
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM projects WHERE projects.user = " + this.iD);
      ResultSet rs = prep.executeQuery();
      projects.clear();
      while (rs.next()) {
        UUID id = UUID.fromString(rs.getString(1));
        String name = rs.getString(3);
        String description = rs.getString(4);
        this.projects.add(new Project(id, name, description));
      }
      prep.close();
      rs.close();
    }

    public void loadSchedule() throws ClassNotFoundException, SQLException {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
      Connection conn = DriverManager.getConnection(urlToDB);
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM schedules WHERE schedules.user = " + this.iD);
      ResultSet rs = prep.executeQuery();
      schedule.clear();
      while (rs.next()) {
        UUID id = UUID.fromString(rs.getString(2));
        long startTime = rs.getLong(3);
        long endTime = rs.getLong(4);
        this.schedule.add(new Block(startTime, endTime, id));
      }
      prep.close();
      rs.close();
    }

  /**
   * Updates task progress, removes passed blocks, return list of tasks who have full progress
   * @return
   */
  public List<Task> updateSchedule() {
      long rightNow = (new Date()).getTime();
      List<Task> complete = new ArrayList<>();
      List<Block> toDelete = schedule.stream().filter(b -> b.getEndTime() < rightNow).collect(Collectors.toList());
      toDelete.forEach(block -> {
        Task t = belongsTo(block.getiD());
        if (t != null) {
          t.blockComplete();
          if (t.getEstimatedTime() < t.getSessionTime()) {
            complete.add(t);
          }
          try {
            updateTaskInDB(t);
          } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
          }
        }
      });
      this.schedule.removeAll(toDelete);
      return complete;
    }

    public Task belongsTo(UUID id) {
      List<Task> t = tasks.stream().filter(task -> task.getID() == id).collect(Collectors.toList());
      if (t.isEmpty()) {
        return null;
      } else if (t.size() == 1) {
        return t.get(0);
      } else {
        throw new RuntimeException("Multiple tasks with same id.");
      }
    }


}
