package ovh.fejker.filetosql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsers {
    private static ArrayList<User> users;
    private static PreparedStatement preparedStatement;
    private static String sort = "";
    private static int records;

    public static ArrayList<User> getUsers () throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        Connection connection = DatabaseHandler.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM users LIMIT ?,?");

        fetchRecords();

        preparedStatement.setInt(1, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(2, (GetUsersServlet.getPage() * 50));
        fetchUsers();
        connection.close();
        return users;
    }

    public static ArrayList<User> getUsers (String search) throws SQLException, ClassNotFoundException {
        users = new ArrayList<>();
        Connection connection = DatabaseHandler.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM users where login LIKE ? OR name LIKE ? OR surname LIKE ? LIMIT ?,?");

        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");

        fetchRecords();

        preparedStatement.setInt(4, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(5, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        connection.close();
        return users;
    }

    public static ArrayList<User> getSortedUsers (String column) throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        sortUsers();
        Connection connection = DatabaseHandler.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM users ORDER BY " + column + " " + sort + " LIMIT ?,?");

        fetchRecords();

        preparedStatement.setInt(1, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(2, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        connection.close();
        return users;
    }

    public static ArrayList<User> getSortedUsers (String column, String search) throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        sortUsers();
        Connection connection = DatabaseHandler.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM users where login LIKE ? OR name LIKE ? OR surname LIKE ? ORDER BY " + column + " " + sort + " LIMIT ?,?");
        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");

        fetchRecords();

        preparedStatement.setInt(4, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(5, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        connection.close();
        return users;
    }

    public static void fetchUsers () throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            users.add
                    (new User(rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)));
        }
    }

    private static void fetchRecords() throws SQLException, ClassNotFoundException {
        records = 0;
        Connection connection = DatabaseHandler.getConnection();
        String query = preparedStatement.toString();
        query = query.substring(query.indexOf(":") + 2, query.indexOf("LIMIT") - 1);
        query = query.substring(0, query.indexOf('*')) + "COUNT(id) " + query.substring(query.indexOf('*') + 2);
        PreparedStatement usersCount = connection.prepareStatement(query);
        ResultSet rs = usersCount.executeQuery();
        rs.next();
        records = rs.getInt(1);
        connection.close();
    }
    public static int getRecords(){
        return records;
    }

    public static void sortUsers() throws SQLException, ClassNotFoundException {
        if(sort.equals("") || sort.equals("DESC")){
            sort = "ASC";
        } else {
            sort = "DESC";
        }
    }
}
