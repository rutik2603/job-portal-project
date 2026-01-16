<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="com.jobportal.db.DBConnection" %>
<%
    // Check if user is logged in
    Integer userId = (Integer) session.getAttribute("user_id");
    String userName = (String) session.getAttribute("name");
    String userEmail = (String) session.getAttribute("email");
    
    if (userId == null) {
        response.sendRedirect("login.html");
        return;
    }
    
    // Fetch user details from database
    String name = "";
    String email = "";
    String gender = "";
    String city = "";
    
    Connection con = null;
    try {
        con = DBConnection.getConnection();
        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            name = rs.getString("name");
            email = rs.getString("email");
            gender = rs.getString("gender");
            city = rs.getString("city");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (con != null) try { con.close(); } catch (Exception e) {}
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile - Job Portal</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 500px;
        }
        
        h2 {
            color: #667eea;
            text-align: center;
            margin-bottom: 10px;
            font-size: 2em;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        input[type="text"],
        input[type="email"],
        select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        input[type="text"]:focus,
        input[type="email"]:focus,
        select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        
        button {
            flex: 1;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.2s;
        }
        
        button:hover {
            transform: translateY(-2px);
        }
        
        .cancel-btn {
            background: #6c757d;
        }
        
        .cancel-btn:hover {
            background: #5a6268;
        }
        
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #667eea;
            text-decoration: none;
            font-weight: bold;
        }
        
        .back-link:hover {
            text-decoration: underline;
        }
        
        .info-box {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #667eea;
        }
        
        .info-box p {
            margin: 5px 0;
            color: #555;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>✏️ Edit Profile</h2>
        <p class="subtitle">Update your personal information</p>
        
        <div class="info-box">
            <p><strong>Current Email:</strong> <%= email %></p>
            <p><small>Note: Email cannot be changed as it's your login ID</small></p>
        </div>
        
        <form action="UpdateProfileServlet" method="post">
            <div class="form-group">
                <label for="name">Full Name:</label>
                <input type="text" id="name" name="name" value="<%= name %>" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= email %>" readonly style="background-color: #f0f0f0; cursor: not-allowed;">
            </div>
            
            <div class="form-group">
                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="">Select Gender</option>
                    <option value="Male" <%= "Male".equals(gender) ? "selected" : "" %>>Male</option>
                    <option value="Female" <%= "Female".equals(gender) ? "selected" : "" %>>Female</option>
                    <option value="Other" <%= "Other".equals(gender) ? "selected" : "" %>>Other</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" id="city" name="city" value="<%= city %>" required>
            </div>
            
            <div class="button-group">
                <button type="submit">💾 Save Changes</button>
                <button type="button" class="cancel-btn" onclick="window.location.href='dashboard.jsp'">❌ Cancel</button>
            </div>
        </form>
        
        <a href="dashboard.jsp" class="back-link">← Back to Dashboard</a>
    </div>
</body>
</html>