<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is logged in
    String name = (String) session.getAttribute("name");
    if (name == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Job Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        h1 { color: #667eea; }
        .menu {
            margin-top: 30px;
        }
        .menu a {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .menu a:hover {
            background: #764ba2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= name %>! </h1>
        <p>You have successfully logged into Job Portal System.</p>
        
        <div class="menu">
            <h3>What would you like to do?</h3>
            <a href="post_job.html"> Post a Job</a>
            <a href="ViewJobsServlet"> View All Jobs</a>
            <a href="edit_profile.jsp"> Edit Profile</a>
            <a href="LogoutServlet"> Logout</a>
        </div>
    </div>
</body>
</html>