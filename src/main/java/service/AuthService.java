package service;

import java.util.UUID;

import model.User;
import repository.UserRepository;
import utility.PassHash;
import utility.Validation;

public class AuthService {
    UserRepository userRepository = new UserRepository();

    public boolean register(String name, String email, String password) {
        // Implement registration logic here
        // What are the steps to register a user? Validate input, check if email already
        // exists, hash password, save user to database, etc.
        // step 1: Validate input
        if (!Validation.name(name)) {
            throw new IllegalArgumentException("Invalid name provided");
        }
        if (!Validation.email(email)) {
            throw new IllegalArgumentException("Invalid email provided");
        }
        if (!Validation.password(password)) {
            throw new IllegalArgumentException("Invalid password provided");
        }

        // step 2: Check if email already exists in database
        if (!userRepository.userExists(email)) {
            // step 3: Hash password
            String hashedPassword = PassHash.getHashedPassword(password);
            // step 4: Save user to database
            UUID userId = UUID.randomUUID();
            User newUser = new User(userId, name, email, hashedPassword);
            return userRepository.saveUser(newUser);
        } else {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public boolean login(String email, String password) {
        User user = userRepository.authenticateUser(email, password);
        if (user != null) {
            SessionManager.getInstance().createSession(user);
            return true;
        }
        return false;
    }

    public boolean logout() {
        // Implement logout logic here
        return SessionManager.getInstance().logout();
    }
}
