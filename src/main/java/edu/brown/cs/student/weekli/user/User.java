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
  private List<Commitment> commitments;
  private List<Task> tasks;
  private List<Project> projects;
  private List<Block> schedule;
  private List<Block> pastBlocks = new ArrayList<>();
  private long breakTime = 0L;

  public User(String iD) throws ClassNotFoundException, SQLException {
    this.schedule = new ArrayList<>();
    this.iD = iD;
    this.commitments = new ArrayList<>();
    this.tasks = new ArrayList<>();
    this.projects = new ArrayList<>();

    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS tasks (" + "id TEXT, " + "user TEXT, " + "startTime INTEGER, "
            + "endTime INTEGER, " + "estTime INTEGER, " + "name TEXT, " + "description TEXT, " +
            "progress FLOAT, " + "sessionTime INTEGER, " + "project TEXT, " + "color TEXT, " +
            "PRIMARY KEY (id));");
    prep.executeUpdate();
    urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
    conn = DriverManager.getConnection(urlToDB);
    prep = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS commitments (" + "id TEXT, " + "user TEXT, " + "startTime " +
            "INTEGER, " + "endTime INTEGER, " + "name TEXT, " + "description TEXT, " + "repeating" +
            " INTEGER, " + "color TEXT, " + "PRIMARY KEY (id));");
    prep.executeUpdate();
    urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
    conn = DriverManager.getConnection(urlToDB);
    prep = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS projects (" + "id TEXT," + "user TEXT," + "name TEXT," +
            "description TEXT," + "PRIMARY KEY (id));");
    prep.executeUpdate();
    urlToDB = "jdbc:sqlite:data/weekli/schedules.sqlite3";
    conn = DriverManager.getConnection(urlToDB);
    prep = conn.prepareStatement(
        "CREATE TABLE IF NOT EXISTS schedules (" + "user TEXT," + "id TEXT," + "startTime " +
            "INTEGER," + "endTime INTEGER, " + "name TEXT, " + "description TEXT, " + "color " +
            "TEXT);");
    prep.executeUpdate();
    prep.close();
  }

  public void updateTaskInDB(Task update) throws ClassNotFoundException, SQLException {
    String projID;
    if (update.getProjectID() == null) {
      projID = "";
    } else {
      projID = update.getProjectID().toString();
    }
    System.out.println(update.getProgress());
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "REPLACE INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, " +
            "sessionTime, project, color)" + " VALUES ('" + update.getID().toString() + "', '" + this.iD + "', " + update.getStartDate() + ", " + update.getEndDate() + ", " + update.getEstimatedTime() + ", '" + update.getName() + "', '" + update.getDescription() + "', " + update.getProgress() + ", " + update.getSessionTime() + ", '" + projID + "', '" + update.getColor() + "');");
    prep.executeUpdate();
    prep.close();
  }

  public UUID addTask(long start, long end, long estTime, String name, String description,
                      long sessionTime, String color, String projID) throws ClassNotFoundException,
      SQLException {
    Task add = new Task(start, end, estTime, name, description, sessionTime, color);
    if (!projID.equals("")) {
      add.addProjectID(UUID.fromString(projID));
    }
    this.tasks.add(add);
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO tasks (id, user, startTime, endTime, estTime, name, description, progress, " +
            "sessionTime, project, color)" + " VALUES ('" + add.getID().toString() + "', '" + this.iD + "', " + add.getStartDate() + ", " + add.getEndDate() + ", " + add.getEstimatedTime() + ", '" + add.getName() + "', '" + add.getDescription() + "', " + add.getProgress() + ", " + add.getSessionTime() + ", '" + projID + "', '" + add.getColor() + "');");
    prep.executeUpdate();
    prep.close();
    return add.getID();
  }

  public void addCommitment(long start, long end, String name, String description,
                            Optional<Long> repeating, String color) throws ClassNotFoundException, SQLException {
    System.out.println("running addCommitment");
    Commitment add = new Commitment(start, end, name, description, repeating, color);
    System.out.println(add);
    this.commitments.add(add);
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    Optional<Long> rep = add.getRepeating();
    PreparedStatement prep;
    System.out.println("before sql...");
    if (rep.isPresent()) {
      System.out.println("repeating");
      prep = conn.prepareStatement(
          "INSERT INTO commitments (id, user, startTime, endTime, name, description, repeating, color)" + " VALUES ('" + add.getID().toString() + "', '" + this.iD + "', " + add.getStartDate() + ", " + add.getEndDate() + ", '" + add.getName() + "', '" + add.getDescription() + "', " + add.getRepeating().get() + ", '" +color+ "');");
    } else {
      System.out.println("non-repeating");
      prep = conn.prepareStatement(
          "INSERT INTO commitments (id, user, startTime, endTime, name, description, color)" + " VALUES " +
              "('" + add.getID().toString() + "', '" + this.iD + "', " + add.getStartDate() + ", " + add.getEndDate() + ", '" + add.getName() + "', '" + add.getDescription() + "', '"+color+"');");
    }
    System.out.println("after sql...");
    prep.executeUpdate();
    prep.close();
  }

  public void addProject(String name, String description, List<UUID> checkpoints) throws
      ClassNotFoundException, SQLException {
    Project add = new Project(name, description);
    this.tasks.stream().filter(t -> checkpoints.contains(t.getID())).forEach(task -> {
      try {
        task.addProjectID(add.getID());
        updateTaskInDB(task);
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    });
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "INSERT INTO projects (id, user, name, description)" + " VALUES ('" + add.getID().toString() + "', '" + this.iD + "', '" + add.getName() + "', '" + add.getDescription() + "');");
    prep.executeUpdate();
    prep.close();
    this.projects.add(add);
  }

  public void storeSchedule() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/schedules.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = null;
    prep = conn.prepareStatement("DELETE FROM schedules WHERE schedules.user = '" + iD + "';");
    prep.executeUpdate();
    for (Block b : schedule) {
      prep = conn.prepareStatement(
          "INSERT INTO schedules (user, id, startTime, endTime, name, description, color)" + " " +
              "VALUES ('" + this.iD + "', '" + b.getiD().toString() + "', " + b.getStartTime() + ", " + b.getEndTime() + ", '" + b.getName() + "', '" + b.getDescription() + "', '" + b.getColor() + "');");
      prep.executeUpdate();
    }
    prep.close();
  }

  public void loadTasks() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "SELECT * FROM tasks WHERE tasks.user = '" + this.iD + "';");
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
      if (projString.equals("")) {
        project = null;
      } else {
        project = UUID.fromString(projString);
      }
      String color = rs.getString(11);
      this.tasks.add(
          new Task(startTime, endTime, estTime, name, description, progress, id, sessionTime,
              project, color));
    }
    prep.close();
    rs.close();
  }

  public void loadCommitments() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "SELECT * FROM commitments WHERE commitments.user = '" + this.iD + "';");
    ResultSet rs = prep.executeQuery();
    commitments.clear();
    while (rs.next()) {
      UUID id = UUID.fromString(rs.getString(1));
      long startTime = rs.getLong(3);
      long endTime = rs.getLong(4);
      String name = rs.getString(5);
      String description = rs.getString(6);
      long rep = rs.getLong(7);
      Optional<Long> repeating;
      if (rep == 0L) {
        repeating = Optional.empty();
      } else {
        repeating = Optional.of(rep);
      }
      String color = rs.getString(8);
      this.commitments.add(new Commitment(startTime, endTime, name, description, id, repeating, color));
    }
    prep.close();
    rs.close();
  }

  public void loadProjects() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "SELECT * FROM projects WHERE projects.user = '" + this.iD + "';");
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

  public void deleteCommitment(String id) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/commitments.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "DELETE FROM commitments WHERE commitments.id = '" + id + "';");
    prep.executeUpdate();
    commitments = commitments.stream().filter(c -> !c.getID().toString().equals(id)).collect(
        Collectors.toList());
  }

  public void deleteTask(String id) throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "DELETE FROM tasks WHERE tasks.id = '" + id + "';");
    prep.executeUpdate();
    tasks = tasks.stream().filter(c -> !c.getID().toString().equals(id)).collect(
        Collectors.toList());
  }

//    public void deleteProject(String id) throws ClassNotFoundException, SQLException {
//      Class.forName("org.sqlite.JDBC");
//      String urlToDB = "jdbc:sqlite:data/weekli/projects.sqlite3";
//      Connection conn = DriverManager.getConnection(urlToDB);
//      PreparedStatement prep = conn.prepareStatement("DELETE FROM projects WHERE projects.id =
//      " + id + ";");
//      prep.executeUpdate();
//      prep = conn.prepareStatement("DELETE FROM tasks WHERE tasks.project = " + id + ";")
//      prep.executeUpdate();
//
//      tasks = tasks.stream().filter(c -> c.getProjectID != null && !c.getProjectID().toString()
//      .equals(id)).collect(Collectors.toList());
//      projects = projects.stream().filter(c -> !c.getID().toString().equals(id)).collect
//      (Collectors.toList());
//    }

  public void loadSchedule() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/schedules.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement(
        "SELECT * FROM schedules WHERE schedules.user = '" + this.iD + "';");
    ResultSet rs = prep.executeQuery();
    schedule.clear();
    while (rs.next()) {
      UUID id = UUID.fromString(rs.getString(2));
      long startTime = rs.getLong(3);
      long endTime = rs.getLong(4);
      String name = rs.getString(5);
      String desc = rs.getString(6);
      String color = rs.getString(7);
      this.schedule.add(new Block(startTime, endTime, id, name, desc, color));
    }
    prep.close();
    rs.close();
  }

  /**
   * Updates task progress, removes passed blocks, return list of tasks who have full progress
   *
   * @return
   */
  public List<Task> updateSchedule() throws SQLException, ClassNotFoundException {
    long rightNow = (new Date()).getTime();
    List<Task> complete = new ArrayList<>();
    List<Block> toDelete = schedule.stream().filter(b -> b.getEndTime() < rightNow).collect(
        Collectors.toList());
    toDelete.forEach(block -> tasks.stream().filter(task -> task.getID() == block.getiD()).forEach(task -> {
      task.blockComplete();
      if (task.getEstimatedTime() < task.getSessionTime()) {
        complete.add(task);
      }
      try {
        updateTaskInDB(task);
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
      }
    }));
    // Live progress
//    List<Block> current =
//        schedule.stream().filter(b -> b.getEndTime() > rightNow && b.getStartTime() <= rightNow).collect(
//            Collectors.toList());
//    if (current.size() == 1) {
//      Task taskToUpdate = belongsToTask(current.get(0).getiD());
//      taskToUpdate.setProgress(taskToUpdate.getProgress() + current.get(0));
//    }

    pastBlocks.addAll(toDelete);
    this.schedule.removeAll(toDelete);
    storeSchedule();
    return complete;
  }

  public Commitment belongsToCommitment(UUID id) {
    List<Commitment> c = commitments.stream().filter(task -> task.getID() == id).collect(
        Collectors.toList());
    if (c.isEmpty()) {
      return null;
    } else if (c.size() == 1) {
      return c.get(0);
    } else {
      throw new RuntimeException("Multiple tasks with same id.");
    }
  }


  public List<Block> getSchedule() {
    return schedule;
  }

  public List<Commitment> getCommitments() {
    return commitments;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public List<Block> getPastBlocks() {
    return pastBlocks;
  }

  public String getID() {
    return iD;
  }

  public void setBreakTime(long breakTime) {
    this.breakTime = breakTime;
  }

  public long getBreakTime() {
    return breakTime;
  }
}
