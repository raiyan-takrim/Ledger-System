import view.AuthView;

public class App {
    public static void main(String[] args) {
        try {
            AuthView.authScreen();
        } finally {
            config.DB.close();
        }
    }
}