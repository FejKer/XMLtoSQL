package ovh.fejker.filetosql;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig
@WebServlet(name = "ImportUsersServlet", value = "/ImportUsersServlet")
public class ImportUsersServlet extends HttpServlet {

    int result = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");                                                             //displaying summary of import to user
        PrintWriter writer = response.getWriter();
        writer.println("<html><body><h1>Import successful " + result + " records affected</h1></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","java", "java");     //setting up mysql connection

            Part filePart = request.getPart("file");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();      //xml upload
            InputStream fileContent = filePart.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);                   //xml parse
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileContent);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("user");

            connection.prepareStatement("TRUNCATE TABLE users;").executeUpdate();    //assuming we want to overwrite existing records

            for(int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("name").item(0).getTextContent();        //getting data from xml
                    String surname = element.getElementsByTagName("surname").item(0).getTextContent();
                    String login = element.getElementsByTagName("login").item(0).getTextContent();
                    String query = "INSERT INTO users (name, surname, login) VALUES(?,?,?)";                //insert into database
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, surname);
                    preparedStatement.setString(3, login);

                    result += (preparedStatement.executeUpdate()) * 3;      //get affected rows
                }
            }

            connection.close();

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            doGet(request, response);
        }

    }
}
