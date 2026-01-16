<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Job Details</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 40px 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
        }
        
        .back-btn {
            background: #f0f0f0;
            color: #333;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-bottom: 20px;
        }
        
        .back-btn:hover {
            background: #e0e0e0;
        }
        
        .job-header {
            border-bottom: 2px solid #f0f0f0;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }
        
        .job-title {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
        }
        
        .company-name {
            font-size: 24px;
            color: #667eea;
            font-weight: 600;
            margin-bottom: 20px;
        }
        
        .job-meta {
            display: flex;
            gap: 30px;
            flex-wrap: wrap;
            margin-top: 20px;
        }
        
        .meta-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .meta-label {
            color: #888;
            font-size: 12px;
            text-transform: uppercase;
            font-weight: 600;
        }
        
        .meta-value {
            color: #333;
            font-size: 16px;
            font-weight: 500;
        }
        
        .section {
            margin: 30px 0;
        }
        
        .section-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 15px;
            font-weight: 600;
        }
        
        .description {
            color: #555;
            line-height: 1.8;
            font-size: 16px;
        }
        
        .apply-btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 40px;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            margin-top: 30px;
        }
        
        .apply-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
        }
        
        .error {
            text-align: center;
            padding: 50px;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="ViewJobsServlet" class="back-btn">← Back to All Jobs</a>
        
        <%
            String jobIdStr = request.getParameter("job_id");
            
            if (jobIdStr == null || jobIdStr.isEmpty()) {
                out.println("<div class='error'><h2>Invalid Job ID</h2></div>");
            } else {
                int jobId = Integer.parseInt(jobIdStr);
                
                String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
                String DB_USER = "root";
                String DB_PASSWORD = "rutik@R262003";
                
                Connection conn = null;
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    
                    String sql = "SELECT j.*, u.name, u.email FROM jobs j " +
                                "JOIN users u ON j.user_id = u.user_id " +
                                "WHERE j.job_id = ?";
                    
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, jobId);
                    rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        String jobTitle = rs.getString("job_title");
                        String companyName = rs.getString("company_name");
                        String description = rs.getString("job_description");
                        String location = rs.getString("location");
                        String salary = rs.getString("salary");
                        String jobType = rs.getString("job_type");
                        Timestamp postedDate = rs.getTimestamp("posted_date");
                        String postedBy = rs.getString("name");
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        %>
                        <div class="job-header">
                            <h1 class="job-title"><%= jobTitle %></h1>
                            <div class="company-name">🏢 <%= companyName %></div>
                            
                            <div class="job-meta">
                                <div class="meta-item">
                                    <span class="meta-label">Location</span>
                                    <span class="meta-value">📍 <%= location %></span>
                                </div>
                                <div class="meta-item">
                                    <span class="meta-label">Job Type</span>
                                    <span class="meta-value">💼 <%= jobType %></span>
                                </div>
                                <% if (salary != null && !salary.isEmpty()) { %>
                                <div class="meta-item">
                                    <span class="meta-label">Salary</span>
                                    <span class="meta-value">💰 <%= salary %></span>
                                </div>
                                <% } %>
                                <div class="meta-item">
                                    <span class="meta-label">Posted On</span>
                                    <span class="meta-value">📅 <%= sdf.format(postedDate) %></span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="section">
                            <h2 class="section-title">Job Description</h2>
                            <div class="description"><%= description %></div>
                        </div>
                        
                        <div class="section">
                            <h2 class="section-title">About this posting</h2>
                            <p class="description">Posted by: <strong><%= postedBy %></strong></p>
                        </div>
                        
                        <button class="apply-btn" onclick="alert('Application feature coming in Phase 3! 🚀')">
                            Apply Now
                        </button>
        <%
                    } else {
                        out.println("<div class='error'><h2>Job not found</h2></div>");
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<div class='error'><h2>Error loading job details</h2></div>");
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (pstmt != null) pstmt.close();
                        if (conn != null) conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        %>
    </div>
</body>
</html>