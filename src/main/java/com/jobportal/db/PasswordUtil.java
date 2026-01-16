package com.jobportal.db;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Hash a password using BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    
    // Verify a password against a hash
    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}