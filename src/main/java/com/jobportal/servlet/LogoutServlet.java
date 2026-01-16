package com.jobportal.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get the current session
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Get user name before destroying session
            String userName = (String) session.getAttribute("name");
            
            // Invalidate the session
            session.invalidate();
            
            System.out.println("✅ User " + (userName != null ? userName : "Unknown") + " logged out successfully");
        }
        
        // Show logout success page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Logged Out</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; justify-content: center; align-items: center; }");
        out.println(".container { background: white; padding: 50px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); text-align: center; max-width: 400px; }");
        out.println("h2 { color: #28a745; margin-bottom: 20px; font-size: 2em; }");
        out.println(".icon { font-size: 4em; margin-bottom: 20px; }");
        out.println("p { color: #666; margin-bottom: 30px; font-size: 1.1em; }");
        out.println("a { display: inline-block; padding: 12px 40px; background: #667eea; color: white; text-decoration: none; border-radius: 8px; font-weight: bold; margin: 5px; }");
        out.println("a:hover { background: #764ba2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='icon'>👋</div>");
        out.println("<h2>Logged Out Successfully!</h2>");
        out.println("<p>Thank you for using Job Portal. See you again soon!</p>");
        out.println("<a href='login.html'>🔐 Login Again</a>");
        out.println("<a href='register.html'>📝 Register</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    // Also handle POST requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}