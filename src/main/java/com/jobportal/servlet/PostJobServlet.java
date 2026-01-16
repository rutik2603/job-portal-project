package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;

public class PostJobServlet extends HttpServlet {
    
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
        
        // Get form parameters
        String company = request.getParameter("company_name");
        String title = request.getParameter("job_title");
        String description = request.getParameter("job_description");
        String location = request.getParameter("location");
        String salaryStr = request.getParameter("salary");
        String jobType = request.getParameter("job_type");
        Integer userId = (Integer) session.getAttribute("user_id");
        
        System.out.println("=== Post Job Debug ===");
        System.out.println("Company: " + company);
        System.out.println("Title: " + title);
        System.out.println("Location: " + location);
        System.out.println("Salary: " + salaryStr);
        System.out.println("Job Type: " + jobType);
        System.out.println("User ID: " + userId);
        
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                return;
            }
            
            // SQL matching your actual table structure
            String sql = "INSERT INTO jobs (company_name, job_title, job_description, location, salary, job_type, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, company);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            
            // Salary is VARCHAR in your table
            if (salaryStr != null && !salaryStr.trim().isEmpty()) {
                ps.setString(5, salaryStr);
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }
            
            ps.setString(6, jobType);
            ps.setInt(7, userId);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ Job posted successfully!");
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Success</title>");
                out.println("<style>");
                out.println("body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; }");
                out.println(".container { background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); text-align: center; max-width: 500px; }");
                out.println("h2 { color: #28a745; margin-bottom: 20px; }");
                out.println(".icon { font-size: 4em; margin-bottom: 20px; }");
                out.println(".job-details { background: #f8f9fa; padding: 15px; border-radius: 8px; margin: 20px 0; text-align: left; }");
                out.println(".job-details p { margin: 5px 0; color: #555; }");
                out.println(".btn-group { display: flex; flex-direction: column; gap: 10px; margin-top: 20px; }");
                out.println("a { display: block; padding: 12px 30px; background: #667eea; color: white; text-decoration: none; border-radius: 5px; }");
                out.println("a:hover { background: #764ba2; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<div class='icon'>✅</div>");
                out.println("<h2>Job Posted Successfully!</h2>");
                out.println("<div class='job-details'>");
                out.println("<p><strong>🏢 Company:</strong> " + company + "</p>");
                out.println("<p><strong>💼 Job Title:</strong> " + title + "</p>");
                out.println("<p><strong>📍 Location:</strong> " + location + "</p>");
                out.println("<p><strong>⏰ Job Type:</strong> " + jobType + "</p>");
                if (salaryStr != null && !salaryStr.trim().isEmpty()) {
                    out.println("<p><strong>💰 Salary:</strong> " + salaryStr + "</p>");
                }
                out.println("</div>");
                out.println("<div class='btn-group'>");
                out.println("<a href='ViewJobsServlet'>👀 View All Jobs</a>");
                out.println("<a href='post_job.html'>➕ Post Another Job</a>");
                out.println("<a href='dashboard.jsp'>🏠 Back to Dashboard</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h3 style='color:red;'>Failed to post job. Try again.</h3>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<!DOCTYPE html>");
            out.println("<html><body style='font-family:Arial; padding:40px;'>");
            out.println("<h3 style='color:red;'>❌ Error: " + e.getMessage() + "</h3>");
            out.println("<p>Check Eclipse console for details.</p>");
            out.println("<a href='post_job.html' style='color:#667eea;'>← Try Again</a>");
            out.println("</body></html>");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }
}