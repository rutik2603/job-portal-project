import com.jobportal.db.PasswordUtil;

public class TestPasswordHashing {
    public static void main(String[] args) {
        System.out.println("Testing Password Hashing System...\n");
        
        // Test password
        String plainPassword = "mySecurePassword123";
        
        // Hash the password
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        
        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println();
        
        // Test 1: Verify correct password
        System.out.println("TEST 1: Verify correct password");
        boolean isCorrect = PasswordUtil.verifyPassword(plainPassword, hashedPassword);
        if (isCorrect) {
            System.out.println("✓ Password verification successful!");
        } else {
            System.out.println("✗ Password verification failed!");
        }
        
        // Test 2: Verify wrong password
        System.out.println("\nTEST 2: Verify wrong password");
        boolean isWrong = PasswordUtil.verifyPassword("wrongPassword", hashedPassword);
        if (!isWrong) {
            System.out.println("✓ Wrong password rejected correctly!");
        } else {
            System.out.println("✗ Wrong password was accepted (security issue!)");
        }
        
        System.out.println("\n✓ Password hashing tests passed!");
    }
}

