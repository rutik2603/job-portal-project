# 💼 Job Portal System

A comprehensive full-stack web application connecting job seekers with employers. Built with Java, JSP, Servlets, JDBC, and MySQL, featuring secure authentication, job management, and application tracking.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)

---

## 🌟 Live Demo

> **Note:** This is a Java-based server application. See [How to Run](#-how-to-run) section for local setup instructions.

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Technologies Used](#️-technologies-used)
- [System Architecture](#️-system-architecture)
- [Database Schema](#️-database-schema)
- [Installation Guide](#-installation-guide)
- [How to Run](#-how-to-run)
- [Project Structure](#-project-structure)
- [Security Features](#-security-features)
- [Screenshots](#-screenshots)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [Author](#-author)

---

## 🎯 Overview

The **Job Portal System** is a full-stack web application designed to bridge the gap between job seekers and employers. The platform provides an intuitive interface for posting jobs, browsing opportunities, and managing applications with robust security and efficient database management.

### Project Highlights

- ✅ **Complete Full-Stack Application** - End-to-end implementation
- 🔒 **Enterprise-Grade Security** - BCrypt password hashing & SQL injection prevention
- 🎨 **Modern UI/UX** - Beautiful gradient designs and responsive layouts
- 💾 **Efficient Database Design** - Normalized schema with proper relationships
- 📱 **Responsive Design** - Works seamlessly across all devices

---

## ✨ Key Features

### 🔐 User Authentication & Management
- **Secure Registration** - Users can create accounts with BCrypt-encrypted passwords
- **Session-Based Login** - Secure authentication with HttpSession management
- **Profile Management** - Edit personal information (name, gender, city)
- **Secure Logout** - Complete session termination and cleanup

### 💼 Job Management
- **Post Jobs** - Employers can create detailed job listings
  - Job title and description
  - Company information
  - Location and salary range
  - Job type (Full-time, Part-time, Contract, Internship)
- **Browse All Jobs** - Beautiful card-based job listing interface
- **Search & Filter** - Advanced filtering by:
  - Keywords (job title, company)
  - Location
  - Job type
- **Job Details Page** - Comprehensive job information display

### 📧 Application System
- **Apply for Jobs** - Submit applications with cover letters
- **Duplicate Prevention** - System checks and prevents multiple applications for the same job
- **Application Status Tracking** - Track status (Pending, Approved, Rejected)
- **Application History** - View all submitted applications

---

## 🛠️ Technologies Used

### Backend Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 8+ | Core programming language |
| Java Servlets | 4.0 | Server-side request handling |
| JSP | 2.3 | Dynamic web page generation |
| JDBC | API 4.2 | Database connectivity |
| Apache Tomcat | 9.0 | Web server and servlet container |
| BCrypt | 0.4 | Password hashing |

### Frontend Technologies
| Technology | Purpose |
|------------|---------|
| HTML5 | Page structure and semantics |
| CSS3 | Styling with gradients and animations |
| JavaScript | Client-side interactivity |
| Responsive Design | Mobile-first approach |

### Database
| Technology | Purpose |
|------------|---------|
| MySQL | 8.0 | Relational database management |
| JDBC | Database connectivity layer |

### Development Tools
- **Eclipse IDE** - Primary development environment
- **MySQL Workbench** - Database design and management
- **Git & GitHub** - Version control
- **Apache Maven** - Build automation (optional)

---

## 🏗️ System Architecture
```
┌─────────────────────────────────────────────────────────┐
│                      CLIENT TIER                        │
│         (Browser - HTML, CSS, JavaScript)               │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP Request/Response
                     ↓
┌─────────────────────────────────────────────────────────┐
│                   PRESENTATION TIER                     │
│            (JSP Pages - Dynamic Views)                  │
│  • login.html      • dashboard.jsp    • jobs.jsp       │
│  • register.html   • post_job.html    • edit_profile   │
└────────────────────┬────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────┐
│                   APPLICATION TIER                      │
│              (Java Servlets - MVC Controllers)          │
│  • RegisterServlet      • PostJobServlet                │
│  • LoginServlet         • ViewJobsServlet               │
│  • LogoutServlet        • ApplyJobServlet               │
│  • UpdateProfileServlet • SearchJobsServlet             │
└────────────────────┬────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────┐
│                      DATA TIER                          │
│                  (JDBC + MySQL)                         │
│  • DBConnection.java (Connection Management)            │
│  • PreparedStatement (SQL Execution)                    │
│  • BCrypt (Password Security)                           │
└────────────────────┬────────────────────────────────────┘
                     │
                     ↓
┌─────────────────────────────────────────────────────────┐
│                   DATABASE TIER                         │
│              (MySQL - job_portal database)              │
│        • users        • jobs        • applications      │
└─────────────────────────────────────────────────────────┘
```

### Architecture Pattern: **MVC (Model-View-Controller)**
- **Model:** Java classes, database entities
- **View:** JSP/HTML pages, user interface
- **Controller:** Java Servlets, business logic

---

## 🗄️ Database Schema

### Entity Relationship Diagram
```
┌─────────────┐         ┌──────────────┐         ┌──────────────────┐
│   USERS     │         │     JOBS     │         │  APPLICATIONS    │
├─────────────┤         ├──────────────┤         ├──────────────────┤
│ user_id (PK)│────┐    │ job_id (PK)  │────┐    │ application_id   │
│ name        │    │    │ user_id (FK) │    │    │ job_id (FK)      │
│ email       │    └───▶│ company_name │    └───▶│ user_id (FK)     │
│ password    │         │ job_title    │         │ applicant_name   │
│ gender      │         │ description  │         │ applicant_email  │
│ city        │         │ location     │         │ cover_letter     │
│ created_at  │         │ salary       │         │ status           │
└─────────────┘         │ job_type     │         │ applied_date     │
                        │ posted_date  │         └──────────────────┘
                        └──────────────┘
      1                        M                         M
      └──────────── Posts ─────┘                         │
                                                          │
      1                                                   │
      └──────────────────── Submits ────────────────────┘
```

### Tables

#### 1. Users Table
```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 2. Jobs Table
```sql
CREATE TABLE jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    job_title VARCHAR(200) NOT NULL,
    job_description TEXT NOT NULL,
    location VARCHAR(100),
    salary VARCHAR(50),
    job_type VARCHAR(50),
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

#### 3. Applications Table
```sql
CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    applicant_email VARCHAR(100) NOT NULL,
    cover_letter TEXT,
    status VARCHAR(50) DEFAULT 'Pending',
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### Database Relationships
- **One User → Many Jobs** (1:M)
- **One User → Many Applications** (1:M)
- **One Job → Many Applications** (1:M)

---

## 📥 Installation Guide

### Prerequisites

Before you begin, ensure you have the following installed:

- ☕ **Java JDK** 8 or higher - [Download](https://www.oracle.com/java/technologies/downloads/)
- 🐱 **Apache Tomcat** 9.0+ - [Download](https://tomcat.apache.org/download-90.cgi)
- 🐬 **MySQL** 8.0+ - [Download](https://dev.mysql.com/downloads/installer/)
- 📝 **Eclipse IDE** for Java EE Developers - [Download](https://www.eclipse.org/downloads/)

### Step-by-Step Installation

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/rutik2603/job-portal-project.git
cd job-portal-project
```

#### 2️⃣ Database Setup

**Open MySQL Workbench or MySQL Command Line**
```sql
-- Create database
CREATE DATABASE job_portal;
USE job_portal;

-- Create users table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create jobs table
CREATE TABLE jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    company_name VARCHAR(200) NOT NULL,
    job_title VARCHAR(200) NOT NULL,
    job_description TEXT NOT NULL,
    location VARCHAR(100),
    salary VARCHAR(50),
    job_type VARCHAR(50),
    posted_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create applications table
CREATE TABLE applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    job_id INT NOT NULL,
    user_id INT NOT NULL,
    applicant_name VARCHAR(100) NOT NULL,
    applicant_email VARCHAR(100) NOT NULL,
    cover_letter TEXT,
    status VARCHAR(50) DEFAULT 'Pending',
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs(job_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

#### 3️⃣ Configure Database Connection

Edit `src/main/java/com/jobportal/db/DBConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/job_portal";
private static final String DB_USER = "root";  // Your MySQL username
private static final String DB_PASSWORD = "your_password";  // Your MySQL password
```

#### 4️⃣ Import Project into Eclipse

1. Open **Eclipse IDE**
2. Go to **File** → **Import**
3. Select **Existing Projects into Workspace**
4. Browse to the cloned project folder
5. Click **Finish**

#### 5️⃣ Add Required Libraries

The project includes necessary JAR files in `src/main/webapp/WEB-INF/lib/`:
- ✅ `jbcrypt-0.4.jar` - Password hashing
- ✅ `mysql-connector-j-8.0.33.jar` - MySQL connectivity

If missing, download and add them to the `lib` folder.

#### 6️⃣ Configure Apache Tomcat in Eclipse

1. Go to **Window** → **Preferences**
2. Navigate to **Server** → **Runtime Environments**
3. Click **Add**
4. Select **Apache Tomcat v9.0**
5. Browse to your Tomcat installation directory
6. Click **Finish**

---

## 🚀 How to Run

### Running the Application

1. **Start MySQL Server**
   - Ensure MySQL is running on port 3306
   - Verify database `job_portal` exists

2. **Run in Eclipse**
   - Right-click on the project
   - Select **Run As** → **Run on Server**
   - Choose **Tomcat v9.0 Server**
   - Click **Finish**

3. **Access the Application**
   
   Open your browser and navigate to:
```
   http://localhost:8080/JobPortalSystem/
```

   **Default Pages:**
   - Login: `http://localhost:8080/JobPortalSystem/login.html`
   - Register: `http://localhost:8080/JobPortalSystem/register.html`

### First Time Setup

1. **Register a New Account**
   - Navigate to the registration page
   - Fill in your details
   - Password will be securely hashed using BCrypt

2. **Login**
   - Use your registered email and password
   - Access the dashboard

3. **Start Using Features**
   - Post jobs
   - Browse available positions
   - Apply for jobs
   - Manage your profile

---

## 📁 Project Structure
```
JobPortalSystem/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── jobportal/
│       │           ├── db/
│       │           │   ├── DBConnection.java          # Database connection handler
│       │           │   └── PasswordUtil.java          # BCrypt utility class
│       │           │
│       │           └── servlet/
│       │               ├── RegisterServlet.java       # User registration logic
│       │               ├── LoginServlet.java          # Authentication logic
│       │               ├── LogoutServlet.java         # Session termination
│       │               ├── DashboardServlet.java      # Dashboard controller
│       │               ├── PostJobServlet.java        # Job creation
│       │               ├── ViewJobsServlet.java       # Job listing display
│       │               ├── JobDetailsServlet.java     # Individual job details
│       │               ├── SearchJobsServlet.java     # Search & filter
│       │               ├── ApplyJobServlet.java       # Application form
│       │               ├── SubmitApplicationServlet.java  # Application submission
│       │               └── UpdateProfileServlet.java  # Profile update
│       │
│       └── webapp/
│           ├── WEB-INF/
│           │   ├── web.xml                           # Servlet configuration
│           │   └── lib/
│           │       ├── jbcrypt-0.4.jar              # Password hashing library
│           │       └── mysql-connector-j-8.0.33.jar # MySQL JDBC driver
│           │
│           ├── META-INF/
│           │   └── MANIFEST.MF
│           │
│           ├── register.html                         # User registration page
│           ├── login.html                            # Login page
│           ├── dashboard.jsp                         # User dashboard
│           ├── post_job.html                         # Job posting form
│           ├── jobs.jsp                              # Job listings page
│           ├── job_details.jsp                       # Job details page
│           ├── edit_profile.jsp                      # Profile editing
│           └── style.css                             # Global styles
│
├── .gitignore                                        # Git ignore rules
└── README.md                                         # Project documentation
```

---

## 🔐 Security Features

### 1. Password Security

#### BCrypt Password Hashing
```java
// Registration - Hash password before storing
String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

// Login - Verify hashed password
if (BCrypt.checkpw(plainPassword, storedHashedPassword)) {
    // Login successful
}
```

**Benefits:**
- ✅ Passwords never stored in plain text
- ✅ Uses strong one-way hashing (irreversible)
- ✅ Includes salt to prevent rainbow table attacks
- ✅ Cost factor of 12 for computational difficulty

### 2. SQL Injection Prevention

#### PreparedStatement Usage
```java
// ✅ SECURE - PreparedStatement with parameters
String sql = "SELECT * FROM users WHERE email = ?";
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1, userEmail);  // Automatically escapes special characters
ResultSet rs = ps.executeQuery();

// ❌ INSECURE - String concatenation (NEVER DO THIS)
String sql = "SELECT * FROM users WHERE email = '" + userEmail + "'";
```

**Protection:**
- ✅ Prevents SQL injection attacks
- ✅ Automatic parameter escaping
- ✅ Type-safe parameter binding

### 3. Session Management
```java
// Create session on login
HttpSession session = request.getSession();
session.setAttribute("user_id", userId);
session.setAttribute("name", userName);
session.setAttribute("email", userEmail);

// Validate session on protected pages
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("user_id") == null) {
    response.sendRedirect("login.html");
    return;
}

// Destroy session on logout
session.invalidate();
```

**Features:**
- ✅ Server-side session storage
- ✅ Automatic session timeout
- ✅ Protected routes validation
- ✅ Secure logout with complete cleanup

### 4. Input Validation

- **Client-side:** HTML5 validation (required, email, min/max length)
- **Server-side:** Additional validation in servlets
- **Database:** Constraints (UNIQUE, NOT NULL, foreign keys)

### 5. Resource Management
```java
Connection con = null;
try {
    con = DBConnection.getConnection();
    // Execute queries
} catch (Exception e) {
    e.printStackTrace();
} finally {
    // Always close connection
    if (con != null) {
        try { 
            con.close(); 
        } catch (Exception e) {}
    }
}
```

**Benefits:**
- ✅ Prevents connection leaks
- ✅ Proper resource cleanup
- ✅ Handles exceptions gracefully

---

## 📸 Screenshots

### 🔐 Authentication

#### Login Page
Clean and modern login interface with form validation.

#### Registration Page
User-friendly registration with secure password handling.

---

### 🏠 Dashboard

#### User Dashboard
Personalized dashboard with quick access to all features:
- Post jobs
- Browse jobs
- Edit profile
- View applications

---

### 💼 Job Management

#### Browse Jobs
Beautiful card-based layout displaying all available jobs with:
- Job title and company
- Location and job type
- Salary information
- Short description

#### Job Details
Comprehensive job information page featuring:
- Complete job description
- Company details
- Requirements
- Application button

#### Post Job
Intuitive form for employers to create job listings:
- Job title and description
- Company information
- Location and salary
- Job type selection

---

### 📧 Applications

#### Application Form
Professional application submission interface:
- Pre-filled user information
- Cover letter text area
- Submit and cancel options

#### Application Success
Confirmation page showing:
- Application details
- Pending status
- Navigation options

---

## 🚀 Future Enhancements

### Planned Features

#### User Experience
- [ ] **Resume Upload** - Allow file uploads (PDF/DOCX) for applications
- [ ] **Email Notifications** - Automated emails for application status updates
- [ ] **Advanced Search** - Filter by salary range, experience level, company size
- [ ] **Save Jobs** - Bookmark jobs for later application
- [ ] **Job Recommendations** - AI-based job matching algorithm

#### User Management
- [ ] **User Roles** - Separate interfaces for Job Seekers vs Employers
- [ ] **Company Profiles** - Dedicated pages showcasing company information
- [ ] **Application Dashboard** - Comprehensive view of all applications
- [ ] **Interview Scheduling** - Built-in calendar for interview management

#### Analytics & Reporting
- [ ] **Dashboard Analytics** - View counts, application rates, success metrics
- [ ] **Employer Dashboard** - Track job performance and applicant analytics
- [ ] **Reports** - Generate PDF reports for applications and job listings

#### Technical Improvements
- [ ] **Connection Pooling** - Implement Apache DBCP or HikariCP for better performance
- [ ] **REST API** - Convert to RESTful architecture for mobile app support
- [ ] **Spring Boot Migration** - Modern framework for enhanced features
- [ ] **Frontend Framework** - React or Angular for richer user experience
- [ ] **Redis Caching** - Improve performance with in-memory caching
- [ ] **Cloud Deployment** - Deploy on AWS, Azure, or Google Cloud Platform
- [ ] **CI/CD Pipeline** - Automated testing and deployment
- [ ] **Docker Support** - Containerization for consistent deployment
- [ ] **Logging Framework** - Log4j or SLF4J for better debugging
- [ ] **Unit Testing** - JUnit and Mockito test coverage

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

### How to Contribute

1. **Fork the Project**
```bash
   git fork https://github.com/rutik2603/job-portal-project.git
```

2. **Create a Feature Branch**
```bash
   git checkout -b feature/AmazingFeature
```

3. **Commit Your Changes**
```bash
   git commit -m 'Add some AmazingFeature'
```

4. **Push to the Branch**
```bash
   git push origin feature/AmazingFeature
```

5. **Open a Pull Request**

### Contribution Guidelines

- Follow Java naming conventions
- Write clear, commented code
- Test thoroughly before submitting
- Update documentation if needed
- Ensure no security vulnerabilities

---

## 👨‍💻 Author

**Rutik Ravindra Nimkarde**

- 📧 Email: rutik2603@gmail.com
- 💼 LinkedIn: [linkedin.com/in/rutik-nimkarde](https://www.linkedin.com/in/rutik-nimkarde)
- 🐙 GitHub: [@rutik2603](https://github.com/rutik2603)

---

## 🙏 Acknowledgments

- **BCrypt Java Library** - For robust password hashing
- **MySQL** - Reliable and efficient database management
- **Apache Tomcat** - Powerful servlet container
- **Eclipse Foundation** - Excellent IDE for Java development
- **Stack Overflow Community** - For invaluable development support

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## ⭐ Show Your Support

If you found this project helpful or interesting, please consider giving it a ⭐ on GitHub!

---

## 📞 Contact & Support

For any queries or support:
- 📧 Email: rutik2603@gmail.com
- 🐛 Issues: [GitHub Issues](https://github.com/rutik2603/job-portal-project/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/rutik2603/job-portal-project/discussions)

---

<div align="center">

### 🌟 Star this repository if you find it useful! 🌟

**Made with ❤️ and ☕ by Rutik Nimkarde**

[⬆ Back to Top](#-job-portal-system)

</div>
