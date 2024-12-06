import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("== Ledger System ==");
        while (true) {
            int choice;
            System.out.println("Login or Register: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("> ");
            choice = input.nextInt();
            if (choice == 1) {
                UserActions.login();
            } else if (choice == 2) {
                UserActions.register();
            } else
                System.out.println("Invalid Input!\n");
        }
    }
}
