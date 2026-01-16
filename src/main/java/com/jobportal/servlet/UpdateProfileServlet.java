package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;

public class UpdateProfileServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.html");
            return;
        }
        
        Integer userId = (Integer) session.getAttribute("user_id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String city = request.getParameter("city");
        
        System.out.println("=== Update Profile Debug ===");
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("City: " + city);
        
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                return;
            }
            
            String sql = "UPDATE users SET name = ?, gender = ?, city = ? WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setString(3, city);
            ps.setInt(4, userId);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                // Update session with new name
                session.setAttribute("name", name);
                
                System.out.println("✅ Profile updated successfully!");
                
                // Success page
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Profile Updated</title>");
                out.println("<style>");
                out.println("body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; }");
                out.println(".container { background: white; padding: 50px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); text-align: center; max-width: 500px; }");
                out.println("h2 { color: #28a745; margin-bottom: 20px; font-size: 2em; }");
                out.println(".icon { font-size: 4em; margin-bottom: 20px; }");
                out.println("p { color: #666; margin-bottom: 10px; font-size: 1.1em; }");
                out.println(".info-box { background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; text-align: left; }");
                out.println(".info-box p { margin: 8px 0; }");
                out.println("a { display: inline-block; padding: 12px 40px; background: #667eea; color: white; text-decoration: none; border-radius: 8px; font-weight: bold; margin: 10px 5px; }");
                out.println("a:hover { background: #764ba2; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<div class='icon'>✅</div>");
                out.println("<h2>Profile Updated Successfully!</h2>");
                out.println("<div class='info-box'>");
                out.println("<p><strong>📝 Name:</strong> " + name + "</p>");
                out.println("<p><strong>👤 Gender:</strong> " + gender + "</p>");
                out.println("<p><strong>📍 City:</strong> " + city + "</p>");
                out.println("</div>");
                out.println("<a href='dashboard.jsp'>🏠 Back to Dashboard</a>");
                out.println("<a href='edit_profile.jsp'>✏️ Edit Again</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h3 style='color:red;'>Failed to update profile. Try again.</h3>");
                out.println("<a href='edit_profile.jsp'>← Try Again</a>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<!DOCTYPE html>");
            out.println("<html><body style='font-family:Arial; padding:40px;'>");
            out.println("<h3 style='color:red;'>❌ Error: " + e.getMessage() + "</h3>");
            out.println("<p>Check Eclipse console for details.</p>");
            out.println("<a href='edit_profile.jsp' style='color:#667eea;'>← Try Again</a>");
            out.println("</body></html>");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }
}