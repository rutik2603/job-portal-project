package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;

public class JobDetailsServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String jobIdStr = request.getParameter("id");
        
        if (jobIdStr == null || jobIdStr.trim().isEmpty()) {
            out.println("<h3 style='color:red;'>Invalid job ID!</h3>");
            out.println("<a href='ViewJobsServlet'>← Back to Jobs</a>");
            return;
        }
        
        Connection con = null;
        try {
            int jobId = Integer.parseInt(jobIdStr);
            con = DBConnection.getConnection();
            
            if (con == null) {
                out.println("<h3 style='color:red;'>Database connection failed!</h3>");
                return;
            }
            
            String sql = "SELECT * FROM jobs WHERE job_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String jobTitle = rs.getString("job_title");
                String company = rs.getString("company_name");
                String description = rs.getString("job_description");
                String location = rs.getString("location");
                String salary = rs.getString("salary");
                String jobType = rs.getString("job_type");
                Timestamp postedDate = rs.getTimestamp("posted_date");
                
                // HTML Output
                out.println("<!DOCTYPE html>");
                out.println("<html lang='en'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>" + jobTitle + " - Job Details</title>");
                out.println("<style>");
                out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
                out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
                out.println(".container { max-width: 900px; margin: 0 auto; }");
                out.println(".back-btn { display: inline-block; padding: 10px 20px; background: white; color: #667eea; text-decoration: none; border-radius: 8px; font-weight: bold; margin-bottom: 20px; }");
                out.println(".back-btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }");
                out.println(".job-details-card { background: white; border-radius: 15px; padding: 40px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
                out.println(".job-header { border-bottom: 3px solid #667eea; padding-bottom: 20px; margin-bottom: 30px; }");
                out.println(".job-title { color: #667eea; font-size: 2.5em; font-weight: bold; margin-bottom: 15px; }");
                out.println(".company-name { color: #555; font-size: 1.4em; font-weight: 600; margin-bottom: 10px; }");
                out.println(".company-name::before { content: '🏢 '; }");
                out.println(".job-meta { display: flex; flex-wrap: wrap; gap: 20px; margin-top: 15px; }");
                out.println(".meta-item { display: flex; align-items: center; color: #666; font-size: 1.1em; }");
                out.println(".meta-item .icon { margin-right: 8px; font-size: 1.3em; }");
                out.println(".section { margin: 30px 0; }");
                out.println(".section-title { color: #333; font-size: 1.5em; font-weight: bold; margin-bottom: 15px; padding-bottom: 10px; border-bottom: 2px solid #f0f0f0; }");
                out.println(".description { color: #555; line-height: 1.8; font-size: 1.1em; white-space: pre-wrap; }");
                out.println(".salary-box { background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; }");
                out.println(".salary-box h3 { color: #28a745; margin-bottom: 10px; }");
                out.println(".salary-box p { color: #555; font-size: 1.2em; font-weight: bold; }");
                out.println(".apply-section { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; border-radius: 10px; text-align: center; margin-top: 30px; }");
                out.println(".apply-section h3 { color: white; margin-bottom: 15px; font-size: 1.5em; }");
                out.println(".apply-btn { display: inline-block; padding: 15px 40px; background: white; color: #667eea; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 1.1em; }");
                out.println(".apply-btn:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.3); }");
                out.println(".posted-date { color: #999; font-size: 0.9em; margin-top: 10px; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                
                out.println("<a href='ViewJobsServlet' class='back-btn'>← Back to All Jobs</a>");
                
                out.println("<div class='job-details-card'>");
                
                // Job Header
                out.println("<div class='job-header'>");
                out.println("<h1 class='job-title'>" + jobTitle + "</h1>");
                out.println("<div class='company-name'>" + company + "</div>");
                
                out.println("<div class='job-meta'>");
                out.println("<div class='meta-item'><span class='icon'>📍</span> " + location + "</div>");
                out.println("<div class='meta-item'><span class='icon'>⏰</span> " + (jobType != null ? jobType : "Not specified") + "</div>");
                if (postedDate != null) {
                    out.println("<div class='meta-item'><span class='icon'>📅</span> Posted: " + postedDate.toString().substring(0, 10) + "</div>");
                }
                out.println("</div>");
                out.println("</div>");
                
                // Salary Section
                if (salary != null && !salary.trim().isEmpty()) {
                    out.println("<div class='salary-box'>");
                    out.println("<h3>💰 Salary</h3>");
                    out.println("<p>" + salary + "</p>");
                    out.println("</div>");
                }
                
                // Job Description
                out.println("<div class='section'>");
                out.println("<h2 class='section-title'>📋 Job Description</h2>");
                out.println("<div class='description'>" + description + "</div>");
                out.println("</div>");
                
                // Apply Section - THIS IS THE IMPORTANT PART!
                HttpSession session = request.getSession(false);
                out.println("<div class='apply-section'>");
                if (session != null && session.getAttribute("user_id") != null) {
                    out.println("<h3>Interested in this position?</h3>");
                    out.println("<a href='ApplyJobServlet?job_id=" + jobId + "' class='apply-btn'>✉️ Apply Now</a>");
                } else {
                    out.println("<h3>Want to apply for this job?</h3>");
                    out.println("<a href='login.html' class='apply-btn'>🔐 Login to Apply</a>");
                }
                out.println("</div>");
                
                out.println("</div>"); // job-details-card
                out.println("</div>"); // container
                out.println("</body>");
                out.println("</html>");
                
            } else {
                out.println("<h3 style='color:red;'>Job not found!</h3>");
                out.println("<a href='ViewJobsServlet'>← Back to Jobs</a>");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            out.println("<a href='ViewJobsServlet'>← Back to Jobs</a>");
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception e) {}
            }
        }
    }
}