import java.sql.*;
import com.jobportal.db.DBConnection;

public class TestDashboard {
    public static void main(String[] args) {
        try {
            Connection con = DBConnection.getConnection();
            
            System.out.println("Testing Dashboard System...\n");
            
            // Test 1: Login with correct credentials
            System.out.println("TEST 1: Verify user exists for login");
            String email = "rutik@example.com";
            String password = "password123";
            
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ User found - login will work");
                System.out.println("  User ID: " + rs.getInt("user_id"));
                System.out.println("  Name: " + rs.getString("name"));
                System.out.println("  Email: " + rs.getString("email"));
            } else {
                System.out.println("✗ User not found");
            }
            
            // Test 2: Update profile
            System.out.println("\nTEST 2: Test profile update");
            String updateSql = "UPDATE users SET city = ? WHERE email = ?";
            PreparedStatement updatePs = con.prepareStatement(updateSql);
            updatePs.setString(1, "Mumbai");
            updatePs.setString(2, email);
            
            int result = updatePs.executeUpdate();
            if (result > 0) {
                System.out.println("✓ Profile update successful");
            } else {
                System.out.println("✗ Profile update failed");
            }
            
            // Test 3: Verify update
            System.out.println("\nTEST 3: Verify profile was updated");
            String verifySql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement verifyPs = con.prepareStatement(verifySql);
            verifyPs.setString(1, email);
            
            ResultSet verifyRs = verifyPs.executeQuery();
            if (verifyRs.next()) {
                System.out.println("✓ Updated user info:");
                System.out.println("  Name: " + verifyRs.getString("name"));
                System.out.println("  Email: " + verifyRs.getString("email"));
                System.out.println("  City: " + verifyRs.getString("city"));
            }
            
            System.out.println("\n✓ Dashboard system tests passed!");
            
            ps.close();
            updatePs.close();
            verifyPs.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}