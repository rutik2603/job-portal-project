package com.jobportal.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestAddMultipleJobs {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rutik@R262003";
    
    public static void main(String[] args) {
        System.out.println("📊 Adding Multiple Test Jobs...\n");
        
        // Job 1: Data Analyst in Pune
        addJob(
            "Data Analyst",
            "Analytics Pro",
            "Looking for a data analyst with strong SQL and Python skills. Experience with data visualization tools required.",
            "Pune",
            "6-10 LPA",
            "Full-time",
            1  // Use your user_id (the one you registered with)
        );
        
        // Job 2: Frontend Developer in Bangalore
        addJob(
            "Frontend Developer",
            "WebTech Solutions",
            "React and Angular developer needed. 2+ years experience. Work on exciting projects with modern tech stack.",
            "Bangalore",
            "10-15 LPA",
            "Full-time",
            1
        );
        
        // Job 3: Marketing Intern in Delhi
        addJob(
            "Marketing Intern",
            "Brand Boost",
            "Exciting internship opportunity for marketing students. Learn digital marketing, SEO, and social media strategies.",
            "Delhi",
            "15,000/month",
            "Internship",
            1
        );
        
        // Job 4: UI/UX Designer in Mumbai
        addJob(
            "UI/UX Designer",
            "Design Hub",
            "Creative UI/UX designer needed for mobile and web applications. Figma and Adobe XD expertise required.",
            "Mumbai",
            "8-12 LPA",
            "Contract",
            1
        );
        
        // Job 5: Backend Developer in Hyderabad
        addJob(
            "Backend Developer",
            "CloudTech Systems",
            "Java Spring Boot developer with microservices experience. Work on scalable cloud-based applications.",
            "Hyderabad",
            "12-18 LPA",
            "Full-time",
            1
        );
        
        // Job 6: Content Writer in Remote
        addJob(
            "Content Writer",
            "ContentPro Media",
            "Remote position for experienced content writer. Tech blog writing experience preferred. Flexible hours.",
            "Remote",
            "4-6 LPA",
            "Part-time",
            1
        );
        
        System.out.println("\n✅ All test jobs added successfully!");
        System.out.println("🎉 Your job portal now has multiple listings!");
    }
    
    private static void addJob(String title, String company, String description, 
                               String location, String salary, String jobType, int userId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            String sql = "INSERT INTO jobs (job_title, company_name, job_description, " +
                        "location, salary, job_type, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, company);
            pstmt.setString(3, description);
            pstmt.setString(4, location);
            pstmt.setString(5, salary);
            pstmt.setString(6, jobType);
            pstmt.setInt(7, userId);
            
            pstmt.executeUpdate();
            
            System.out.println("✅ Added: " + title + " at " + company + " (" + location + ")");
            
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.out.println("❌ Error adding job: " + title);
            e.printStackTrace();
        }
    }
}