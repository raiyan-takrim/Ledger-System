package utility;

import org.mindrot.jbcrypt.BCrypt;

public class PassHash {
    public static String getHashedPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Perform basic validation if `hashedPassword` looks like a bcrypt hash
            if (hashedPassword == null || !hashedPassword.startsWith("$2")) {
                throw new IllegalArgumentException("Invalid or corrupted password hash format.");
            }
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException ex) {
            System.err.println("Error verifying password: " + ex.getMessage());
            return false; // Always return false for invalid hashes
        }
    }
}
