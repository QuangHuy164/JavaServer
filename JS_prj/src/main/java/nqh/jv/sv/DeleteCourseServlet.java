package nqh.jv.sv;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/deleteCourseServlet")
public class DeleteCourseServlet extends HttpServlet {

    private Connection connection;
    private PreparedStatement statement;

    @Override
    public void init() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_project", "e2101064", "PsPrKeudbHq");
            statement = connection.prepareStatement("DELETE FROM Courses WHERE course_id = ? AND name = ? AND teacher = ?");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String course_id = request.getParameter("course_id");
    	String name = request.getParameter("name");
    	String teacher = request.getParameter("teacher"); // Corrected parameter name
       
        try {
			statement.setString(1, course_id);
            statement.setString(2, name);
            statement.setString(3, teacher);

            int result = statement.executeUpdate();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("<b>" + result + " course deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}