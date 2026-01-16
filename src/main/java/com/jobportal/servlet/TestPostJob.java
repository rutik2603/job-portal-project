package com.jobportal.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestPostJob {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
        String DB_USER = "root";
        String DB_PASSWORD = "rutik@R262003";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "INSERT INTO jobs (user_id, company_name, job_title, " +
                        "job_description, location, salary, job_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1); // Test user_id
            pstmt.setString(2, "Tech Corp");
            pstmt.setString(3, "Software Developer");
            pstmt.setString(4, "Looking for experienced Java developer");
            pstmt.setString(5, "Mumbai");
            pstmt.setString(6, "8-12 LPA");
            pstmt.setString(7, "Full-time");
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ SUCCESS! Job posted to database!");
            }
            
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}