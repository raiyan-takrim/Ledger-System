import service.SessionManager;
import view.AuthView;

public class App {
    public static void main(String[] args) {
        try {
            AuthView.authScreen();
        } finally {
            SessionManager.getInstance().destroySession();
            config.DB.close();
        }
    }
}