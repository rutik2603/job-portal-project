package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.jobportal.db.DBConnection;
import java.sql.*;

public class ApplyJobServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
        String userName = (String) session.getAttribute("name");
        String userEmail = (String) session.getAttribute("email");
        String jobIdStr = request.getParameter("job_id");
        
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
            
            // Get job details
            String sqlJob = "SELECT job_title, company_name FROM jobs WHERE job_id = ?";
            PreparedStatement psJob = con.prepareStatement(sqlJob);
            psJob.setInt(1, jobId);
            ResultSet rsJob = psJob.executeQuery();
            
            if (!rsJob.next()) {
                out.println("<h3 style='color:red;'>Job not found!</h3>");
                out.println("<a href='ViewJobsServlet'>← Back to Jobs</a>");
                return;
            }
            
            String jobTitle = rsJob.getString("job_title");
            String companyName = rsJob.getString("company_name");
            
            // Check if user already applied
            String sqlCheck = "SELECT * FROM applications WHERE job_id = ? AND user_id = ?";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, jobId);
            psCheck.setInt(2, userId);
            ResultSet rsCheck = psCheck.executeQuery();
            
            if (rsCheck.next()) {
                // Already applied
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Already Applied</title>");
                out.println("<style>");
                out.println("body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; padding: 20px; }");
                out.println(".container { background: white; padding: 50px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); text-align: center; max-width: 500px; }");
                out.println("h2 { color: #ffc107; margin-bottom: 20px; font-size: 2em; }");
                out.println(".icon { font-size: 4em; margin-bottom: 20px; }");
                out.println("p { color: #666; margin-bottom: 20px; font-size: 1.1em; }");
                out.println("a { display: inline-block; padding: 12px 30px; background: #667eea; color: white; text-decoration: none; border-radius: 8px; font-weight: bold; margin: 5px; }");
                out.println("a:hover { background: #764ba2; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<div class='icon'>⚠️</div>");
                out.println("<h2>Already Applied!</h2>");
                out.println("<p>You have already applied for <strong>" + jobTitle + "</strong> at <strong>" + companyName + "</strong>.</p>");
                out.println("<p>Application Status: <strong style='color:#ffc107;'>" + rsCheck.getString("status") + "</strong></p>");
                out.println("<a href='ViewJobsServlet'>← Browse Other Jobs</a>");
                out.println("<a href='dashboard.jsp'>🏠 Dashboard</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            // Show application form
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Apply for Job</title>");
            out.println("<style>");
            out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }");
            out.println(".container { max-width: 700px; margin: 50px auto; background: white; padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); }");
            out.println("h2 { color: #667eea; margin-bottom: 10px; font-size: 2em; }");
            out.println(".subtitle { color: #666; margin-bottom: 30px; }");
            out.println(".job-info { background: #f8f9fa; padding: 20px; border-radius: 10px; margin-bottom: 30px; border-left: 4px solid #667eea; }");
            out.println(".job-info h3 { color: #333; margin-bottom: 10px; }");
            out.println(".job-info p { color: #555; margin: 5px 0; }");
            out.println(".form-group { margin-bottom: 20px; }");
            out.println("label { display: block; margin-bottom: 8px; color: #333; font-weight: 500; }");
            out.println("input, textarea { width: 100%; padding: 12px; border: 2px solid #ddd; border-radius: 5px; font-size: 16px; font-family: inherit; }");
            out.println("input:focus, textarea:focus { outline: none; border-color: #667eea; }");
            out.println("input[readonly] { background-color: #f0f0f0; cursor: not-allowed; }");
            out.println("textarea { min-height: 150px; resize: vertical; }");
            out.println(".button-group { display: flex; gap: 10px; margin-top: 30px; }");
            out.println("button { flex: 1; padding: 14px; border: none; border-radius: 5px; font-size: 16px; font-weight: bold; cursor: pointer; }");
            out.println(".submit-btn { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }");
            out.println(".submit-btn:hover { transform: translateY(-2px); }");
            out.println(".cancel-btn { background: #6c757d; color: white; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>📧 Apply for Job</h2>");
            out.println("<p class='subtitle'>Fill in your application details</p>");
            
            out.println("<div class='job-info'>");
            out.println("<h3>" + jobTitle + "</h3>");
            out.println("<p><strong>🏢 Company:</strong> " + companyName + "</p>");
            out.println("</div>");
            
            out.println("<form action='SubmitApplicationServlet' method='post'>");
            out.println("<input type='hidden' name='job_id' value='" + jobId + "'>");
            
            out.println("<div class='form-group'>");
            out.println("<label>Full Name:</label>");
            out.println("<input type='text' name='name' value='" + userName + "' readonly>");
            out.println("</div>");
            
            out.println("<div class='form-group'>");
            out.println("<label>Email:</label>");
            out.println("<input type='email' name='email' value='" + userEmail + "' readonly>");
            out.println("</div>");
            
            out.println("<div class='form-group'>");
            out.println("<label>Cover Letter / Why do you want this job?</label>");
            out.println("<textarea name='cover_letter' placeholder='Tell us why you are a great fit for this position...' required></textarea>");
            out.println("</div>");
            
            out.println("<div class='button-group'>");
            out.println("<button type='submit' class='submit-btn'>✉️ Submit Application</button>");
            out.println("<button type='button' class='cancel-btn' onclick='history.back()'>❌ Cancel</button>");
            out.println("</div>");
            
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
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