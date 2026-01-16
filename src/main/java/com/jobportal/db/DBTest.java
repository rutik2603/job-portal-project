package com.jobportal.db;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();
            if (con != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Connection is null!");
            }
        } catch (Exception e) {
            System.out.println("Error connecting to database!");
            e.printStackTrace();
        }
    }
}