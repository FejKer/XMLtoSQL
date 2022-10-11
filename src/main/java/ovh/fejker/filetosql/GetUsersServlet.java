package ovh.fejker.filetosql;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "GetUsersServlet", value = "/GetUsersServlet")
public class GetUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String searchInput = request.getParameter("searchInput");
            int page;
            if(request.getParameter("page") != null) page = Integer.parseInt(request.getParameter("page"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","java", "java");
            PreparedStatement preparedStatement;

            if(searchInput != null && !searchInput.equals("")) {
                preparedStatement = connection.prepareStatement("SELECT * FROM users where login LIKE ? OR name LIKE ? OR surname LIKE ?");
                preparedStatement.setString(1, "%" + searchInput + "%");
                preparedStatement.setString(2, "%" + searchInput + "%");
                preparedStatement.setString(3, "%" + searchInput + "%");
            } else {
                preparedStatement = connection.prepareStatement("SELECT * FROM users");
            }

            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();
            int columns = rsMeta.getColumnCount();

            HashMap<Long, ArrayList<String>> userData = new HashMap<Long, ArrayList<String>>();
            ArrayList<User> userList = new ArrayList<User>();

            while (rs.next()) {
                //ArrayList<String> userList = new ArrayList<String>();
                userList.add
                        (new User(rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4)));
            }

            request.setAttribute("users", userList);

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
