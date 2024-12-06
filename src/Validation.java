import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    // Example password pattern: at least 8 characters, at least one letter, one
    // number, and one special character
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final String NAME_PATTERN = "^[A-Za-z0-9]+$";

    public static boolean name(String name) {
        Pattern namePattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean email(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean password(String password) {
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }
}
