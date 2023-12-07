package nqh.jv.sv;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

	@WebServlet("/addCourseServlet")
	public class AddCourseServlet extends HttpServlet {
		private Connection conn;
		private PreparedStatement ps;
	
	@Override
	public void init() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_project","e2101064","PsPrKeudbHq");
			ps = conn.prepareStatement("INSERT INTO courses (courseid, name, teacher) VALUES(?, ?, ?)");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String courseid = req.getParameter("courseid");
		String name = req.getParameter("name");
		String teacher = req.getParameter("teacher");
		
		try {
			
			ps.setString(1,  courseid);
			ps.setString(2,  name);
			ps.setString(3,  teacher);
			int result = ps.executeUpdate();
			PrintWriter out = res.getWriter();
			out.println(result + " course created");
		
  
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	@Override
	public void destroy( ) {
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}