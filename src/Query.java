public class Query {
    static String getAll = "SELECT * FROM ?";
    static String addUser = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
    static String userExists = "SELECT COUNT(*) FROM users WHERE email = ?";
    static String getPassword = "SELECT password from users WHERE email = ?";
}
