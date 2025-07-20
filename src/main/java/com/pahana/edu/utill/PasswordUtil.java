package com.pahana.edu.utill;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Hash a password with automatically generated salt
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    // Check if a plain password matches the hashed version
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}