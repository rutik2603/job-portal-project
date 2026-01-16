<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Browse Jobs</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            padding: 20px;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .search-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .search-form {
            display: grid;
            grid-template-columns: 2fr 1.5fr 1.5fr 1fr;
            gap: 15px;
            align-items: end;
        }
        
        .form-group {
            display: flex;
            flex-direction: column;
        }
        
        .form-group label {
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
            font-size: 14px;
        }
        
        .form-group input,
        .form-group select {
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .search-btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: transform 0.2s;
        }
        
        .search-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
        }
        
        .results-info {
            background: #f8f9fa;
            padding: 15px 20px;
            border-radius: 5px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .results-count {
            font-weight: 600;
            color: #333;
        }
        
        .clear-filters {
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
        }
        
        .clear-filters:hover {
            text-decoration: underline;
        }
        
        .job-card {
            background: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }
        
        .job-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        
        .job-title {
            font-size: 24px;
            color: #333;
            margin-bottom: 10px;
        }
        
        .company-name {
            color: #667eea;
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 15px;
        }
        
        .job-details {
            display: flex;
            gap: 20px;
            margin: 15px 0;
            flex-wrap: wrap;
        }
        
        .detail-item {
            display: flex;
            align-items: center;
            gap: 5px;
            color: #666;
        }
        
        .job-description {
            color: #555;
            line-height: 1.6;
            margin: 15px 0;
        }
        
        .view-btn {
            background: #667eea;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        
        .view-btn:hover {
            background: #764ba2;
        }
        
        .no-jobs {
            text-align: center;
            padding: 50px;
            background: white;
            border-radius: 10px;
        }
        
        @media (max-width: 768px) {
            .search-form {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>🔍 Find Your Dream Job</h1>
        <p>Search from thousands of opportunities</p>
    </div>
    
    <!-- Search & Filter Section -->
    <div class="search-container">
        <form action="SearchJobsServlet" method="GET" class="search-form">
            <div class="form-group">
                <label for="keyword">🔎 Keywords</label>
                <input type="text" id="keyword" name="keyword" 
                       placeholder="Job title, company, or keywords..." 
                       value="<%= request.getAttribute("keyword") != null ? request.getAttribute("keyword") : "" %>">
            </div>
            
            <div class="form-group">
                <label for="location">📍 Location</label>
                <input type="text" id="location" name="location" 
                       placeholder="City or state..." 
                       value="<%= request.getAttribute("location") != null ? request.getAttribute("location") : "" %>">
            </div>
            
            <div class="form-group">
                <label for="job_type">💼 Job Type</label>
                <select id="job_type" name="job_type">
                    <option value="all" <%= "all".equals(request.getAttribute("jobType")) ? "selected" : "" %>>All Types</option>
                    <option value="Full-time" <%= "Full-time".equals(request.getAttribute("jobType")) ? "selected" : "" %>>Full-time</option>
                    <option value="Part-time" <%= "Part-time".equals(request.getAttribute("jobType")) ? "selected" : "" %>>Part-time</option>
                    <option value="Contract" <%= "Contract".equals(request.getAttribute("jobType")) ? "selected" : "" %>>Contract</option>
                    <option value="Internship" <%= "Internship".equals(request.getAttribute("jobType")) ? "selected" : "" %>>Internship</option>
                </select>
            </div>
            
            <button type="submit" class="search-btn">Search</button>
        </form>
    </div>
    
    <%
        List<Map<String, Object>> jobsList = 
            (List<Map<String, Object>>) request.getAttribute("jobsList");
        
        String keyword = (String) request.getAttribute("keyword");
        String location = (String) request.getAttribute("location");
        String jobType = (String) request.getAttribute("jobType");
        
        boolean hasFilters = (keyword != null && !keyword.isEmpty()) || 
                            (location != null && !location.isEmpty()) || 
                            (jobType != null && !jobType.isEmpty() && !"all".equals(jobType));
    %>
    
    <!-- Results Info -->
    <% if (jobsList != null) { %>
        <div class="results-info">
            <span class="results-count">
                Found <%= jobsList.size() %> job<%= jobsList.size() != 1 ? "s" : "" %>
                <% if (hasFilters) { %>
                    matching your search
                <% } %>
            </span>
            <% if (hasFilters) { %>
                <a href="ViewJobsServlet" class="clear-filters">✕ Clear Filters</a>
            <% } %>
        </div>
    <% } %>
    
    <!-- Jobs List -->
    <%
        if (jobsList != null && !jobsList.isEmpty()) {
            for (Map<String, Object> job : jobsList) {
    %>
                <div class="job-card">
                    <h2 class="job-title"><%= job.get("job_title") %></h2>
                    <div class="company-name">🏢 <%= job.get("company_name") %></div>
                    
                    <div class="job-details">
                        <div class="detail-item">📍 <%= job.get("location") %></div>
                        <div class="detail-item">💼 <%= job.get("job_type") %></div>
                        <% if (job.get("salary") != null && !job.get("salary").toString().isEmpty()) { %>
                            <div class="detail-item">💰 <%= job.get("salary") %></div>
                        <% } %>
                    </div>
                    
                    <div class="job-description">
                        <%= job.get("job_description").toString().substring(0, 
                            Math.min(150, job.get("job_description").toString().length())) %>...
                    </div>
                    
                    <!-- ✅ CHANGED: Now links to JobDetailsServlet instead of job_details.jsp -->
                    <a href="JobDetailsServlet?id=<%= job.get("job_id") %>" class="view-btn">
                        View Details →
                    </a>
                </div>
    <%
            }
        } else {
    %>
            <div class="no-jobs">
                <h2>😔 No jobs found</h2>
                <% if (hasFilters) { %>
                    <p>Try adjusting your search filters</p>
                    <br>
                    <a href="ViewJobsServlet" class="view-btn">View All Jobs</a>
                <% } else { %>
                    <p>Check back soon for new opportunities!</p>
                <% } %>
            </div>
    <%
        }
    %>
</body>
</html>