package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the session
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        if (session == null || session.getAttribute("user_id") == null) {
            // User not logged in, redirect to login
            response.sendRedirect("login.html");
            return;
        }

        // User is logged in, get session data
        int userId = (Integer) session.getAttribute("user_id");
        String name = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Dashboard</title>");
        out.println("<link rel='stylesheet' href='style.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>Welcome, " + name + "!</h2>");
        out.println("<div class='dashboard-info'>");
        out.println("<p><strong>User ID:</strong> " + userId + "</p>");
        out.println("<p><strong>Name:</strong> " + name + "</p>");
        out.println("<p><strong>Email:</strong> " + email + "</p>");
        out.println("</div>");
        out.println("<div class='dashboard-links'>");
        out.println("<a href='edit_profile.html' class='btn'>Edit Profile</a>");
        out.println("<a href='LogoutServlet' class='btn btn-logout'>Logout</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}