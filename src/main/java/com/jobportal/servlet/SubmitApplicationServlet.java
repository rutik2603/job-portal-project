package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;

public class SubmitApplicationServlet extends HttpServlet {
    
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
        String jobIdStr = request.getParameter("job_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String coverLetter = request.getParameter("cover_letter");
        
        System.out.println("=== Submit Application Debug ===");
        System.out.println("User ID: " + userId);
        System.out.println("Job ID: " + jobIdStr);
        System.out.println("Cover Letter Length: " + (coverLetter != null ? coverLetter.length() : 0));
        
        Connection con = null;
        try {
            int jobId = Integer.parseInt(jobIdStr);
            con = DBConnection.getConnection();
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                return;
            }
            
            // Get job details for confirmation
            String sqlJob = "SELECT job_title, company_name FROM jobs WHERE job_id = ?";
            PreparedStatement psJob = con.prepareStatement(sqlJob);
            psJob.setInt(1, jobId);
            ResultSet rsJob = psJob.executeQuery();
            
            String jobTitle = "";
            String companyName = "";
            if (rsJob.next()) {
                jobTitle = rsJob.getString("job_title");
                companyName = rsJob.getString("company_name");
            }
            
            // Insert application
            String sql = "INSERT INTO applications (job_id, user_id, applicant_name, applicant_email, cover_letter, status) VALUES (?, ?, ?, ?, ?, 'Pending')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            ps.setInt(2, userId);
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, coverLetter);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ Application submitted successfully!");
                
                // Success page
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Application Submitted</title>");
                out.println("<style>");
                out.println("body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; padding: 20px; }");
                out.println(".container { background: white; padding: 50px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); text-align: center; max-width: 600px; }");
                out.println("h2 { color: #28a745; margin-bottom: 20px; font-size: 2.5em; }");
                out.println(".icon { font-size: 5em; margin-bottom: 20px; animation: bounce 1s; }");
                out.println("@keyframes bounce { 0%, 100% { transform: translateY(0); } 50% { transform: translateY(-20px); } }");
                out.println("p { color: #666; margin-bottom: 15px; font-size: 1.1em; line-height: 1.6; }");
                out.println(".job-info { background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; text-align: left; }");
                out.println(".job-info p { margin: 8px 0; }");
                out.println(".status-badge { display: inline-block; padding: 8px 20px; background: #ffc107; color: #333; border-radius: 20px; font-weight: bold; margin: 15px 0; }");
                out.println(".button-group { display: flex; gap: 10px; margin-top: 30px; flex-wrap: wrap; }");
                out.println("a { flex: 1; min-width: 150px; padding: 12px 20px; text-decoration: none; border-radius: 8px; font-weight: bold; text-align: center; }");
                out.println(".primary-btn { background: #667eea; color: white; }");
                out.println(".primary-btn:hover { background: #764ba2; }");
                out.println(".secondary-btn { background: #6c757d; color: white; }");
                out.println(".secondary-btn:hover { background: #5a6268; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<div class='icon'>🎉</div>");
                out.println("<h2>Application Submitted!</h2>");
                out.println("<p>Your application has been successfully submitted.</p>");
                
                out.println("<div class='job-info'>");
                out.println("<p><strong>💼 Position:</strong> " + jobTitle + "</p>");
                out.println("<p><strong>🏢 Company:</strong> " + companyName + "</p>");
                out.println("<p><strong>👤 Applicant:</strong> " + name + "</p>");
                out.println("<p><strong>📧 Email:</strong> " + email + "</p>");
                out.println("</div>");
                
                out.println("<div class='status-badge'>⏳ Status: Pending Review</div>");
                
                out.println("<p style='margin-top: 20px;'>The employer will review your application and contact you soon.</p>");
                
                out.println("<div class='button-group'>");
                out.println("<a href='ViewJobsServlet' class='primary-btn'>🔍 Browse More Jobs</a>");
                out.println("<a href='MyApplicationsServlet' class='secondary-btn'>📋 My Applications</a>");
                out.println("<a href='dashboard.jsp' class='secondary-btn'>🏠 Dashboard</a>");
                out.println("</div>");
                
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<h3 style='color:red;'>Failed to submit application. Try again.</h3>");
                out.println("<a href='ApplyJobServlet?job_id=" + jobId + "'>← Try Again</a>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<!DOCTYPE html>");
            out.println("<html><body style='font-family:Arial; padding:40px;'>");
            out.println("<h3 style='color:red;'>❌ Error: " + e.getMessage() + "</h3>");
            out.println("<p>Check Eclipse console for details.</p>");
            out.println("<a href='ViewJobsServlet' style='color:#667eea;'>← Back to Jobs</a>");
            out.println("</body></html>");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }
}