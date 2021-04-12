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
        String statement = "SELECT * FROM users WHERE users.id = \"" + id + "\";";

        PreparedStatement prep = this.connection.prepareStatement(statement);
        ResultSet rs = prep.executeQuery();
        User loggingIn = null;

        if (rs.next()) {
            if (BCrypt.checkpw(pw, rs.getString(2))) {
                loggingIn = new User(rs.getString(1));
            }
        }
        prep.close();
        rs.close();
        return loggingIn;
    }

    public User signUp(String id, String pw) throws SQLException, ClassNotFoundException {
        User loggingIn = null;
        if (id.equals("") || pw.equals("")) {
            return loggingIn;
        }

        String hashpw = BCrypt.hashpw(pw, BCrypt.gensalt());

        PreparedStatement prep = this.connection.prepareStatement("SELECT * FROM users WHERE users.id = \"" +
                id +"\";");
        ResultSet rs = prep.executeQuery();;
        if (!rs.next()) {
            prep = this.connection.prepareStatement("INSERT INTO users (id, password) VALUES (\"" + id + "\"," +
                    "\"" + hashpw + "\");");
            prep.executeUpdate();
            loggingIn = new User(id);
        }
        prep.close();
        rs.close();
        return loggingIn;
    }
}
