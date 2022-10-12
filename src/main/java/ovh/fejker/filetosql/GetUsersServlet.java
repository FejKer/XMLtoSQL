package ovh.fejker.filetosql;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetUsersServlet", value = "/GetUsersServlet")
public class GetUsersServlet extends HttpServlet {
    private static int page;
    private String search = "";

    @Override
    public void init() throws ServletException {
        page = 1;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(!search.equals("") && search != null){
                request.setAttribute("users", GetUsers.getUsers(search));
            } else {
                request.setAttribute("users", GetUsers.getUsers());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("getusers.jsp");     //redirect to getusers.jsp
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("searchInput") != null) {
            search = request.getParameter("searchInput");
        }
        response.sendRedirect(request.getContextPath() + "/GetUsersServlet");
    }

    public static int getPage(){
        return page;
    }
    public static void incrementPage() {
        page++;
    }
    public static void decrementPage() {
        page--;
    }
}
