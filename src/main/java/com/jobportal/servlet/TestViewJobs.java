package com.jobportal.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestViewJobs {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
        String DB_USER = "root";
        String DB_PASSWORD = "rutik@R262003";
        
        System.out.println("🔍 Testing View Jobs Functionality...\n");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "SELECT j.*, u.name FROM jobs j " +
                        "JOIN users u ON j.user_id = u.user_id " +
                        "ORDER BY j.posted_date DESC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            System.out.println("📋 JOBS LIST:");
            System.out.println("=".repeat(80));
            
            while (rs.next()) {
                count++;
                System.out.println("\n🆔 Job ID: " + rs.getInt("job_id"));
                System.out.println("🏢 Company: " + rs.getString("company_name"));
                System.out.println("💼 Title: " + rs.getString("job_title"));
                System.out.println("📍 Location: " + rs.getString("location"));
                System.out.println("💰 Salary: " + rs.getString("salary"));
                System.out.println("📝 Type: " + rs.getString("job_type"));
                System.out.println("👤 Posted by: " + rs.getString("name"));
                System.out.println("📅 Date: " + rs.getTimestamp("posted_date"));
                System.out.println("-".repeat(80));
            }
            
            System.out.println("\n✅ SUCCESS! Found " + count + " job(s) in database!");
            
            if (count == 0) {
                System.out.println("⚠️  No jobs found. Make sure you ran TestPostJob first!");
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}