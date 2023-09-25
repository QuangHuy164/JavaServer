import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Database connection parameters
    private String jdbcURL = "jdbc:mysql://localhost:3306/your_database_name";
    private String jdbcUsername = "your_username";
    private String jdbcPassword = "your_password";
    
    // JDBC variables
    private Connection connection;
    
    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        switch (action) {
            case "add":
                addStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                listStudents(request, response);
                break;
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO students (lastname, firstname) VALUES (?, ?)");
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, firstname);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("index.jsp");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int number = Integer.parseInt(request.getParameter("number"));
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE students SET lastname=?, firstname=? WHERE number=?");
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, firstname);
            preparedStatement.setInt(3, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("index.jsp");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int number = Integer.parseInt(request.getParameter("number"));
        
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM students WHERE number=?");
            preparedStatement.setInt(1, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("index.jsp");
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            
            request.setAttribute("students", resultSet);
            request.getRequestDispatcher("student-list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
