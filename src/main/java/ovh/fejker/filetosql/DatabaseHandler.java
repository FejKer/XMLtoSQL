package ovh.fejker.filetosql;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {

    public static Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java",
                    "java",
                    "java"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
