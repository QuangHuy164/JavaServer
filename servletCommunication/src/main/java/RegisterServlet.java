@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection conn;
    private PreparedStatement ps;

    @Override
    public void init(ServletConfig config) throws ServletException {
        // Initialize database connection, similar to the LoginServlet
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Check if the email already exists in the database (optional)

            // Insert the new user into the database
            ps = conn.prepareStatement("INSERT INTO account (email, password) VALUES (?, ?)");
            ps.setString(1, email);
            ps.setString(2, password);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Registration successful, forward to a success page or login page
                request.setAttribute("message", "Registration successful! You can now login.");
                request.getRequestDispatcher("login.html").forward(request, response);
            } else {
                // Registration failed, forward to an error page or registration page
                request.setAttribute("error", "Registration failed. Please try again.");
                request.getRequestDispatcher("registration.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        // Close the database connection
    }
}
