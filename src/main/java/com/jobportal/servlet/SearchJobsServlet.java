package com.jobportal.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet("/SearchJobsServlet")
public class SearchJobsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rutik@R262003";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get search parameters
        String keyword = request.getParameter("keyword");
        String location = request.getParameter("location");
        String jobType = request.getParameter("job_type");
        
        List<Map<String, Object>> jobsList = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Build dynamic SQL query
            StringBuilder sql = new StringBuilder(
                "SELECT j.*, u.name FROM jobs j " +
                "JOIN users u ON j.user_id = u.user_id WHERE 1=1"
            );
            
            List<String> params = new ArrayList<>();
            
            // Add keyword search (searches in title, company, description)
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (j.job_title LIKE ? OR j.company_name LIKE ? OR j.job_description LIKE ?)");
                String keywordPattern = "%" + keyword.trim() + "%";
                params.add(keywordPattern);
                params.add(keywordPattern);
                params.add(keywordPattern);
            }
            
            // Add location filter
            if (location != null && !location.trim().isEmpty()) {
                sql.append(" AND j.location LIKE ?");
                params.add("%" + location.trim() + "%");
            }
            
            // Add job type filter
            if (jobType != null && !jobType.trim().isEmpty() && !jobType.equals("all")) {
                sql.append(" AND j.job_type = ?");
                params.add(jobType);
            }
            
            sql.append(" ORDER BY j.posted_date DESC");
            
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> job = new HashMap<>();
                job.put("job_id", rs.getInt("job_id"));
                job.put("job_title", rs.getString("job_title"));
                job.put("company_name", rs.getString("company_name"));
                job.put("job_description", rs.getString("job_description"));
                job.put("location", rs.getString("location"));
                job.put("salary", rs.getString("salary"));
                job.put("job_type", rs.getString("job_type"));
                job.put("posted_date", rs.getTimestamp("posted_date"));
                job.put("posted_by", rs.getString("name"));
                jobsList.add(job);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("jobsList", jobsList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("location", location);
        request.setAttribute("jobType", jobType);
        request.getRequestDispatcher("jobs.jsp").forward(request, response);
    }
}