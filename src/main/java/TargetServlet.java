import java.io.IOException;
import java.sql.Connection;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/targetServlet")
public class TargetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
		PrintWriter out = res.getWriter();
		Cookie[] cookies = req.getCookies();
		for(int i = 0; i < cookies.length; i++) {
			out.println(cookies[i].getName());
			out.println(cookies[i].getValue());
		}
		if(session != null) {
			String userName = (String) session.getAttribute("user");
			out.println("Hello " + userName);
		} else {
			out.println("Session has ended");
		}
	}
}
