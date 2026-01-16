package com.jobportal.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ViewJobsServlet")
public class ViewJobsServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rutik@R262003";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Map<String, Object>> jobsList = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "SELECT j.*, u.name FROM jobs j " +
                        "JOIN users u ON j.user_id = u.user_id " +
                        "ORDER BY j.posted_date DESC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> job = new HashMap<>();
                job.put("job_id", rs.getInt("job_id"));
                job.put("company_name", rs.getString("company_name"));
                job.put("job_title", rs.getString("job_title"));
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
            
            request.setAttribute("jobsList", jobsList);
            request.getRequestDispatcher("jobs.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}