package com.jobportal.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private static Connection con = null;
    
    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                // Load MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("MySQL Driver loaded successfully!");
                
                // Connection details
                String url = "jdbc:mysql://localhost:3306/job_portal";
                String user = "root";
                String password = "rutik@R262003";
                
                System.out.println("Attempting to connect to: " + url);
                System.out.println("Using username: " + user);
                
                // Create connection
                con = DriverManager.getConnection(url, user, password);
                
                if (con != null) {
                    System.out.println("✅ Database connected successfully!");
                } else {
                    System.out.println("❌ Connection object is null!");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("Error type: " + e.getClass().getName());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
