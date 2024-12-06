import java.util.Scanner;

public class TransactionMethods {

    private static char decision;
    private static double percentage, amount;
    private static String description;
    static Scanner input = new Scanner(System.in);

    public static void savings() {
        System.out.println("== Savings ==");
        System.out.print("Are you sure you want to activate it? (Y/N) : ");
        decision = input.next().charAt(0);
        if (decision == 'Y'){
            System.out.print("\nPlease enter the percentage you wish to deduct from the next debit: ");
            percentage = input.nextDouble();
            System.out.println("Savings settings added successfully!!!");
        }
        System.out.println("Savings Settings not added");

    }

    public static void debit() {
        System.out.println("== Debit ==");
        System.out.print("Enter Amount: ");//ensure there are positive  and there's limit
        amount = input.nextDouble();
        System.out.print("Enter description: ");//not more than 100 character plus looping if exceed
        description = input.nextLine();
        System.out.println("Debit Successfully Recorded!!!");

    }

    public static void credit() {
        System.out.println("== Credit ==");
        System.out.print("Enter Amount: ");
        amount = input.nextDouble();
        System.out.print("Enter description: ");
        description = input.nextLine();//not more than 100 character plus looping if exceed
        System.out.println("credit Successfully Recorded!!!");

    }
}
