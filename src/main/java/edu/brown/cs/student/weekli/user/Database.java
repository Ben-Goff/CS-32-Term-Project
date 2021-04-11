package edu.brown.cs.student.weekli.user;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class Database {

    Connection connection;

    public Database() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:data/weekli/users.sqlite3";
        this.connection = DriverManager.getConnection(urlToDB);;
        PreparedStatement prep = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                + "id TEXT,"
                + "password TEXT);");
        prep.executeUpdate();
        prep.close();
    }

    public User signIn(String id, String pw) throws SQLException, ClassNotFoundException {
        System.out.println("ran-1");
        String hashid = BCrypt.hashpw(id, BCrypt.gensalt());
        String hashpw = BCrypt.hashpw(pw, BCrypt.gensalt());
        System.out.println("ran0");
        PreparedStatement prep = this.connection.prepareStatement("SELECT * WHERE users.id = "+hashid+" AND users.password = "+hashpw+";");
        System.out.println("ran1");
        ResultSet rs = prep.executeQuery();
        User loggingIn = null;
        System.out.println("ran2");
        if (rs.next()) {
            loggingIn = new User(rs.getString(1));
        }
        System.out.println("ran3");
        prep.close();
        rs.close();
        return loggingIn;
    }

    public User signUp(String id, String pw) throws SQLException, ClassNotFoundException {
        String hashid = BCrypt.hashpw(id, BCrypt.gensalt());
        String hashpw = BCrypt.hashpw(pw, BCrypt.gensalt());
        PreparedStatement prep = this.connection.prepareStatement("SELECT * WHERE users.id = "+hashid+";");
        ResultSet rs = prep.executeQuery();
        User loggingIn = null;
        if (!rs.next()) {
            prep = this.connection.prepareStatement("INSERT INTO users (id, password) VALUES ("+hashid+", "+hashpw+");");
            prep.executeUpdate();
            loggingIn = new User(rs.getString(1));
        }
        prep.close();
        rs.close();
        return loggingIn;
    }
}
