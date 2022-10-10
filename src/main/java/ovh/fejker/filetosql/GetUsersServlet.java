package ovh.fejker.filetosql;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "GetUsersServlet", value = "/GetUsersServlet")
public class GetUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","java", "java");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");

            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columns = rsMeta.getColumnCount();

            HashMap<Long, ArrayList<String>> userData = new HashMap<Long, ArrayList<String>>();

            while (rs.next()) {
                ArrayList<String> userList = new ArrayList<String>();
                for (int i = 2; i <= columns; i++) {
                    userList.add(rs.getString(i));
                }
                userData.put(rs.getLong(1), userList);
            }

            request.setAttribute("users", userData);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("getusers.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
