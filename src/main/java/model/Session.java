package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {
    private UUID userId;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime lastActivity;
    private final long SESSION_TIMEOUT = 30 * 60 * 1000; // 30 minutes in milliseconds

    public Session(UUID userId, String email, String name) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }
    public void updateActivity() {
        this.lastActivity = LocalDateTime.now();
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(lastActivity.plusMinutes(SESSION_TIMEOUT / (60 * 1000)));
    }
}
