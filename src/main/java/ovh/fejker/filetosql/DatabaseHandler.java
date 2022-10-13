package ovh.fejker.filetosql;

import java.sql.*;

public class DatabaseHandler {

    public static Connection getConnection() throws ClassNotFoundException {
        try {
            Class.forName(GetUsersServlet.getDriver());
            return DriverManager.getConnection(
                    GetUsersServlet.getUrl(),
                    GetUsersServlet.getUser(),
                    GetUsersServlet.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
