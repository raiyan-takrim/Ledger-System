import java.util.Scanner;
public class Screens {
    public static void LoginRegScreen() {
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
                if(UserActions.login()) {
                    System.out.println("Login Successful!!!");
                    break;
                }
            } else if (choice == 2) {
                if(UserActions.register()) {
                    System.out.println("Registration Successful!!!");
                    System.out.println("Please login to continue.");
                }
            } else
                System.out.println("Invalid Input!\n");
        }
        input.close();
    }
}
