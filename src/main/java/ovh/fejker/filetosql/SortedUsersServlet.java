package ovh.fejker.filetosql;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "SortedUsersServlet", value = "/SortedUsersServlet")
public class SortedUsersServlet extends HttpServlet {
    private String column = "";
    private String search = "";
    private ArrayList<User> users;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("page") != null){
                GetUsersServlet.setPage(Integer.parseInt(request.getParameter("page")));
            }

            if(!search.equals("") && search != null){
                users = GetUsers.getSortedUsers(column, search);
                if(GetUsersServlet.getPage() > GetUsersServlet.getNumberOfPages()){
                    GetUsersServlet.setPage(1);
                }
            }else if(GetUsersServlet.getSearch() != null){
                search = GetUsersServlet.getSearch();
                users = GetUsers.getSortedUsers(column, search);
                GetUsersServlet.setSearch();
            } else {
                users = GetUsers.getSortedUsers(column);
            }

            request.setAttribute("users", users);
            request.setAttribute("numberOfPages", GetUsersServlet.getNumberOfPages());
            request.setAttribute("currentPage", GetUsersServlet.getPage());
            request.setAttribute("numberOfUsers", GetUsers.getRecords());

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
