package nqh.jv.sv;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

	@WebServlet("/addStudentServlet")
	public class AddStudentServlet extends HttpServlet {
		private Connection conn;
		private PreparedStatement ps;
	
	@Override
	public void init() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_project","e2101064","PsPrKeudbHq");
			ps = conn.prepareStatement("INSERT INTO Students (stdID, firstname, lastname, email) VALUES(?, ?, ?, ?)");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String stdID = req.getParameter("stdID");
		String firstname = req.getParameter("firstname");
		String email = req.getParameter("email");
		String lastname = req.getParameter("lastname");
		try {
			ps.setString(1,  stdID);
			ps.setString(2,  firstname);
			ps.setString(3, lastname);
			ps.setString(4, email);
			int result = ps.executeUpdate();
			PrintWriter out = res.getWriter();
			out.println(result + " student created");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	private boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";       		
		Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		System.out.println(email);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
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