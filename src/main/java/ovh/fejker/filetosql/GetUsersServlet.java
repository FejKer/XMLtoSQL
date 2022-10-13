package ovh.fejker.filetosql;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@WebServlet(name = "GetUsersServlet", value = "/GetUsersServlet")
public class GetUsersServlet extends HttpServlet {
    private static int page;
    private static String search = "";
    private static ArrayList<User> users;
    private static String url = "";
    private static String user = "";
    private static String password = "";
    private static String driver = "";

    @Override
    public void init() throws ServletException {
        Properties props = new Properties();

        ServletContext ctx = this.getServletContext();
        InputStream is = ctx.getResourceAsStream("/WEB-INF/db.properties");
        InputStreamReader isr = new InputStreamReader(is);
        try {
            props.load(isr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        driver = props.getProperty("jdbc.driver");
        url = props.getProperty("jdbc.url");
        user = props.getProperty("jdbc.username");
        password = props.getProperty("jdbc.password");
        page = 1;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("page") != null){
                page = Integer.parseInt(request.getParameter("page"));
            }

            if(!search.equals("") && search != null){
                users = GetUsers.getUsers(search);
            } else {
                users = GetUsers.getUsers();
            }

           // response.getWriter().println("users: " + users);
           // response.getWriter().println("numberOfPages: " + getNumberOfPages());
           // response.getWriter().println("currentPage: " + getPage());
           // response.getWriter().println("numberOfUsers: " + GetUsers.getRecords());

            request.setAttribute("users", users);
            request.setAttribute("numberOfPages", getNumberOfPages());
            request.setAttribute("currentPage", getPage());
            request.setAttribute("numberOfUsers", GetUsers.getRecords());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            RequestDispatcher requestDispatcher = requestDispatcher = request.getRequestDispatcher("getusers.jsp");
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

    public static int getNumberOfPages(){
        if(GetUsers.getRecords() % 50 > 0){
            return GetUsers.getRecords() / 50 + 1;
        } else {
            return GetUsers.getRecords() / 50;
        }
    }
    public static int getPage(){
        return page;
    }
    public static void setPage(int p){
        page = p;
    }
    public static String getSearch(){
        return search;
    }
    public static void setSearch(){
        search = "";
    }
    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getDriver() {
        return driver;
    }
}
