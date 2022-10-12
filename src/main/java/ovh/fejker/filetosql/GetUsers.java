package ovh.fejker.filetosql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetUsers {
    private static ArrayList<User> users;
    private static PreparedStatement preparedStatement;
    private static String sort = "";

    public static ArrayList<User> getUsers () throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        preparedStatement = DatabaseHandler.getConnection().prepareStatement("SELECT * FROM users LIMIT ?,?");
        preparedStatement.setInt(1, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(2, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        return users;
    }

    public static ArrayList<User> getUsers (String search) throws SQLException, ClassNotFoundException {
        users = new ArrayList<>();
        preparedStatement = DatabaseHandler.getConnection().prepareStatement("SELECT * FROM users where login LIKE ? OR name LIKE ? OR surname LIKE ? LIMIT ?,?");

        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");
        preparedStatement.setInt(4, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(5, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        return users;
    }

    public static ArrayList<User> getSortedUsers (String column) throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        sortUsers();

        preparedStatement = DatabaseHandler.getConnection().prepareStatement("SELECT * FROM users ORDER BY " + column + " " + sort + " LIMIT ?,?");
        preparedStatement.setInt(1, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(2, (GetUsersServlet.getPage() * 50));

        fetchUsers();
        return users;
    }

    public static ArrayList<User> getSortedUsers (String column, String search) throws ClassNotFoundException, SQLException {
        users = new ArrayList<>();
        sortUsers();

        preparedStatement = DatabaseHandler.getConnection().prepareStatement("SELECT * FROM users where login LIKE ? OR name LIKE ? OR surname LIKE ? ORDER BY " + column + " " + sort + " LIMIT ?,?");

        preparedStatement.setString(1, "%" + search + "%");
        preparedStatement.setString(2, "%" + search + "%");
        preparedStatement.setString(3, "%" + search + "%");
        preparedStatement.setInt(4, (GetUsersServlet.getPage() - 1) * 50);
        preparedStatement.setInt(5, (GetUsersServlet.getPage() * 50));

        fetchUsers();
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

    public static void sortUsers() throws SQLException, ClassNotFoundException {
        if(sort.equals("") || sort.equals("DESC")){
            sort = "ASC";
        } else {
            sort = "DESC";
        }
    }
}
