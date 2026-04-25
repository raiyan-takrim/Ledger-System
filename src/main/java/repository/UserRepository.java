package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DB;
import model.User;

public class UserRepository {
    // All the queries related to user operations will be defined here. This class
    // will interact with the database to perform CRUD operations for users.
    private final String USER_EXISTS_QUERY = "SELECT COUNT(*) FROM users WHERE email = ?";
    private final String CREATE_USER_QUERY = "INSERT INTO users (id, name, email, hashed_password) VALUES (?, ?, ?, ?)";

    public boolean userExists(String email) {
        try (Connection conn = DB.connect()) {
            PreparedStatement stmt = conn.prepareStatement(USER_EXISTS_QUERY);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveUser(User user) {
        try (Connection conn = DB.connect()) {
            PreparedStatement stmt = conn.prepareStatement(CREATE_USER_QUERY);
            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHashedPassword());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save user to database" + e.getMessage());
        }
    }
}
