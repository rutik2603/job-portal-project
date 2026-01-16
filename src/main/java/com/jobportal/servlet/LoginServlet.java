package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                return;
            }
            
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                
                // Verify password using BCrypt
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Login successful - create session
                    HttpSession session = request.getSession();
                    session.setAttribute("user_id", rs.getInt("user_id"));
                    session.setAttribute("name", rs.getString("name"));
                    session.setAttribute("email", rs.getString("email"));
                    
                    // Redirect to dashboard
                    response.sendRedirect("dashboard.jsp");
                } else {
                    // Wrong password
                    out.println("<h3 style='color:red;'>Invalid email or password!</h3>");
                    out.println("<p><a href='login.html'>Try again</a></p>");
                }
            } else {
                // Email not found
                out.println("<h3 style='color:red;'>Invalid email or password!</h3>");
                out.println("<p><a href='login.html'>Try again</a></p>");
                out.println("<p><a href='register.html'>Don't have an account? Register here</a></p>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }
}