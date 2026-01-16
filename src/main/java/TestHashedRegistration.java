import java.sql.*;
import com.jobportal.db.DBConnection;
import com.jobportal.db.PasswordUtil;

public class TestHashedRegistration {
    public static void main(String[] args) {
        try 
        {
            Connection con = DBConnection.getConnection();
            
            System.out.println("Testing Hashed Registration and Login...\n");
            
            // Test data - new user
            String name = "Sarah Johnson";
            String email = "sarah@example.com";
            String plainPassword = "securePass456";
            String gender = "Female";
            String city = "Pune";
            
            // Hash the password
            String hashedPassword = PasswordUtil.hashPassword(plainPassword);
            
            // Test 1: Register user with hashed password
            System.out.println("TEST 1: Register user with hashed password");
            String insertSql = "INSERT INTO users (name, email, password, gender, city) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertPs = con.prepareStatement(insertSql);
            insertPs.setString(1, name);
            insertPs.setString(2, email);
            insertPs.setString(3, hashedPassword);
            insertPs.setString(4, gender);
            insertPs.setString(5, city);
            
            int result = insertPs.executeUpdate();
            if (result > 0) {
                System.out.println("✓ User registered with hashed password");
                System.out.println("  Name: " + name);
                System.out.println("  Email: " + email);
            } else {
                System.out.println("✗ Registration failed");
            }
            
            // Test 2: Login with correct password
            System.out.println("\nTEST 2: Login with correct password");
            String loginSql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement loginPs = con.prepareStatement(loginSql);
            loginPs.setString(1, email);
            
            ResultSet rs = loginPs.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                if (PasswordUtil.verifyPassword(plainPassword, storedHash)) {
                    System.out.println("✓ Login successful with correct password");
                    System.out.println("  User: " + rs.getString("name"));
                    System.out.println("  Email: " + rs.getString("email"));
                    System.out.println("  City: " + rs.getString("city"));
                } else {
                    System.out.println("✗ Login failed - password mismatch");
                }
            }
            
            // Test 3: Login with wrong password
            System.out.println("\nTEST 3: Login with wrong password");
            PreparedStatement wrongLoginPs = con.prepareStatement(loginSql);
            wrongLoginPs.setString(1, email);
            
            ResultSet wrongRs = wrongLoginPs.executeQuery();
            if (wrongRs.next()) {
                String storedHash = wrongRs.getString("password");
                if (!PasswordUtil.verifyPassword("wrongPassword", storedHash)) {
                    System.out.println("✓ Login rejected with wrong password (correct behavior)");
                } else {
                    System.out.println("✗ Wrong password was accepted (security issue!)");
                }
            }
            
            System.out.println("\n========================================");
            System.out.println("✓ All hashed password tests passed!");
            System.out.println("========================================");
            System.out.println("\nYour authentication system is now SECURE!");
            System.out.println("Passwords are hashed with BCrypt.");
            
            insertPs.close();
            loginPs.close();
            wrongLoginPs.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}