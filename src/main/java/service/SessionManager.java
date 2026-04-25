package service;

import model.Session;
import model.User;

public class SessionManager {
    private static SessionManager instance;
    private Session currentSession;

    private SessionManager() {
        // Private constructor to prevent instantiation
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void createSession(User user) {
        this.currentSession = new Session(user.getId(), user.getName(), user.getEmail());
    }
    public Session getCurrentSession() {
        if(currentSession != null && currentSession.isExpired()){
            destroySession();
            System.out.println("Session expired. Please login again.");
            return null;
        }
        return currentSession;
    }

    public void updateActivity() {
        if (currentSession != null) {
            currentSession.updateActivity();
        }
    }
    public boolean isSessionActive() {
        return currentSession != null && !currentSession.isExpired();
    }

    public void destroySession() {
        this.currentSession = null;
    }
    public boolean logout(){
        if (currentSession != null) {
            destroySession();
            return true;
        }
        return false;
    }

}
