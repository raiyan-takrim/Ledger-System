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
                    UserProfile();
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

    public static void UserProfile(){
        Scanner input = new Scanner(System.in);

            System.out.println("\n== Welcome, "+" ==");
            System.out.println("Balance: ");
            System.out.println("Savings: ");
            System.out.println("Loan: ");
    
            System.out.println("\n== Transaction ==");
            System.out.println("1.Debit");
            System.out.println("2.Credit");
            System.out.println("3.History");
            System.out.println("4.Savings");
            System.out.println("5.Credit Loan");
            System.out.println("6.Deposit Interest Predictor");
            System.out.println("7.Logout");
            System.out.print(">");
            int choice = input.nextInt();

            switch(choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("Thank you for using Ledger System");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                
            }
    }
}
