package com.jobportal.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestSearchJobs {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rutik@R262003";
    
    public static void main(String[] args) {
        System.out.println("🔍 Testing Search & Filter Functionality...\n");
        
        // Test 1: Search by keyword
        System.out.println("=".repeat(80));
        System.out.println("TEST 1: Search by keyword 'Developer'");
        System.out.println("=".repeat(80));
        testSearch("Developer", null, null);
        
        // Test 2: Search by location
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST 2: Search by location 'Mumbai'");
        System.out.println("=".repeat(80));
        testSearch(null, "Mumbai", null);
        
        // Test 3: Filter by job type
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST 3: Filter by job type 'Full-time'");
        System.out.println("=".repeat(80));
        testSearch(null, null, "Full-time");
        
        // Test 4: Combined search
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST 4: Combined - keyword 'Software' + location 'Mumbai' + type 'Full-time'");
        System.out.println("=".repeat(80));
        testSearch("Software", "Mumbai", "Full-time");
        
        // Test 5: No results
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TEST 5: Search with no results 'xyz123'");
        System.out.println("=".repeat(80));
        testSearch("xyz123", null, null);
    }
    
    private static void testSearch(String keyword, String location, String jobType) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Build dynamic SQL query (same logic as servlet)
            StringBuilder sql = new StringBuilder(
                "SELECT j.*, u.name FROM jobs j " +
                "JOIN users u ON j.user_id = u.user_id WHERE 1=1"
            );
            
            List<String> params = new ArrayList<>();
            
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (j.job_title LIKE ? OR j.company_name LIKE ? OR j.job_description LIKE ?)");
                String keywordPattern = "%" + keyword.trim() + "%";
                params.add(keywordPattern);
                params.add(keywordPattern);
                params.add(keywordPattern);
            }
            
            if (location != null && !location.trim().isEmpty()) {
                sql.append(" AND j.location LIKE ?");
                params.add("%" + location.trim() + "%");
            }
            
            if (jobType != null && !jobType.trim().isEmpty() && !jobType.equals("all")) {
                sql.append(" AND j.job_type = ?");
                params.add(jobType);
            }
            
            sql.append(" ORDER BY j.posted_date DESC");
            
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }
            
            ResultSet rs = pstmt.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println("\n🆔 Job ID: " + rs.getInt("job_id"));
                System.out.println("💼 Title: " + rs.getString("job_title"));
                System.out.println("🏢 Company: " + rs.getString("company_name"));
                System.out.println("📍 Location: " + rs.getString("location"));
                System.out.println("📝 Type: " + rs.getString("job_type"));
                System.out.println("-".repeat(80));
            }
            
            if (count == 0) {
                System.out.println("\n❌ No jobs found matching the criteria");
            } else {
                System.out.println("\n✅ SUCCESS! Found " + count + " job(s)");
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