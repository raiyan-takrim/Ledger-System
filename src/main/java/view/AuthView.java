package view;
import java.util.Scanner;
import controller.AuthController;

public class AuthView {
    private static final Scanner INPUT = new Scanner(System.in);
    private final static AuthController authController;

    static {
        authController = new AuthController();
    }

    public static void authScreen() {
        while (true) {
            System.out.println("== Ledger System ==");
            System.out.println("Login or Register: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("> ");

            int choice = readChoice();
            switch (choice) {
                case 1:
                    // Implement login logic here
                    throw new UnsupportedOperationException("Login functionality not implemented yet.");
                case 2:
                    if (handleRegister()) {
                        System.out.println("\nRegistration Successful!!!");
                        System.out.println("Please login to continue.\n");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Input!\n");
            }
        }
    }

    private static int readChoice() {
        String raw = INPUT.nextLine();
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String prompt(String label) {
        System.out.print(label);
        return INPUT.nextLine().trim();
    }

    private static boolean handleRegister() {
        System.out.println("== Register ==");
        String name = prompt("Name: ");
        String email = prompt("Email: ");
        String password = prompt("Password: ");
        String confirmPassword = prompt("Confirm Password: ");

        if (!password.equals(confirmPassword)) {
            System.out.println("Password not matched!\n");
            return false;
        }

        try {
            return authController.register(name, email, password);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
            return false;
        }
    }
}