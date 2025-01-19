
import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

public class PassHash {

    /**
     * Generates a hashed password using jBCrypt.
     *
     * @param plainPassword the plain text password to hash
     * @return the hashed password
     */
    public static String hashPassword(String plainPassword) {
        return hashpw(plainPassword, gensalt());
    }

    /**
     * Verifies a plain-text password against a hashed password.
     *
     * @param plainPassword the plain text password
     * @param hashedPassword the hashed password to verify against
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Perform basic validation if `hashedPassword` looks like a bcrypt hash
            if (hashedPassword == null || !hashedPassword.startsWith("$2")) {
                throw new IllegalArgumentException("Invalid or corrupted password hash format.");
            }
            return checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException ex) {
            // Log the error or notify an administrator
            System.err.println("Error verifying password: " + ex.getMessage());
            return false; // Always return false for invalid hashes
        }
    }
}
