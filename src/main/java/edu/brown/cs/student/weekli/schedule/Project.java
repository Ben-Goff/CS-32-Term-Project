package edu.brown.cs.student.weekli.schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class
Project {

  private String name;
  private String description;
  private UUID iD;

  public Project(String name, String description) {
    this.iD = UUID.randomUUID();
    this.name = name;
    this.description = description;
  }

  public Project(UUID iD, String name, String description) {
    this.iD = iD;
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public double getProgress() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:data/weekli/tasks.sqlite3";
    Connection conn = DriverManager.getConnection(urlToDB);
    PreparedStatement prep = conn.prepareStatement("SELECT tasks.progress FROM tasks WHERE tasks.project = " + this.iD.toString() + ");");
    ResultSet rs = prep.executeQuery();
    double total = 0;
    int size = rs.getFetchSize();
    while (rs.next()) {
      total += rs.getFloat(1);
    }
    prep.close();
    rs.close();
    return total / size;
  }

  public String getDescription() {
    return description;
  }

  public UUID getID() {
    return iD;
  }
}
