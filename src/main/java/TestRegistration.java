import java.sql.*;
import com.jobportal.db.DBConnection;

public class TestRegistration {
    public static void main(String[] args) {
        try {
            // Get database connection
            Connection con = DBConnection.getConnection();
            
            if (con == null) {
                System.out.println("ERROR: Database connection failed!");
                return;
            }
            
            System.out.println("Database connected successfully!");
            
            // Simulate form data (as if user filled the registration form)
            String name = "Rutik Sharma";
            String email = "rutik@example.com";
            String password = "password123";
            String gender = "Male";
            String city = "Nagpur";
            
            System.out.println("\nAttempting to insert test data...");
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("City: " + city);
            
            // Insert data into users table
            String sql = "INSERT INTO users (name, email, password, gender, city) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, city);
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                System.out.println("\nSUCCESS! Data inserted into database!");
            } else {
                System.out.println("\nFAILED! Data was not inserted!");
            }
            
            // Verify data was saved by retrieving it
            System.out.println("\n--- Verifying data in database ---");
            String selectSql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement selectPs = con.prepareStatement(selectSql);
            selectPs.setString(1, email);
            ResultSet rs = selectPs.executeQuery();
            
            if (rs.next()) {
                System.out.println("Data found in database:");
                System.out.println("ID: " + rs.getInt("user_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("City: " + rs.getString("city"));
            } else {
                System.out.println("Data not found in database!");
            }
            
            ps.close();
            selectPs.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}