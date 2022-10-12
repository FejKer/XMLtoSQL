package ovh.fejker.filetosql;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SortedUsersServlet", value = "/SortedUsersServlet")
public class SortedUsersServlet extends HttpServlet {
    private String column = "";
    private String search = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(!search.equals("") && search != null){
                request.setAttribute("users", GetUsers.getSortedUsers(column, search));
            } else {
                request.setAttribute("users", GetUsers.getSortedUsers(column));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("getusers.jsp");     //redirect to getusers.jsp
            requestDispatcher.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        column = request.getParameter("column");
        if(request.getParameter("searchInput") != null) {
            search = request.getParameter("searchInput");
        }
        response.sendRedirect(request.getContextPath() + "/SortedUsersServlet");
    }
}
