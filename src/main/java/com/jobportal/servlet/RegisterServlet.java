package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;  // ← FIXED!
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String city = request.getParameter("city");
        
        Connection con = null;
        try {
            con = DBConnection.getConnection();  // ← FIXED!
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                out.println("<p>Please check your database settings.</p>");
                return;
            }
            
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            
            String sql = "INSERT INTO users (name, email, password, gender, city) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);
            ps.setString(4, gender);
            ps.setString(5, city);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                out.println("<h3 style='color:green;'>Registration Successful!</h3>");
                out.println("<p><a href='login.html'>Click here to login</a></p>");
            } else {
                out.println("<h3 style='color:red;'>Registration Failed. Try again.</h3>");
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