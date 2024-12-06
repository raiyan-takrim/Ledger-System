import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserActions {
    private static String name, email, password, confirmPassword;
    static Scanner input = new Scanner(System.in);//hello

    public static void login() {
        boolean isValid = false;
        while (!isValid) {
            System.out.println("== Please enter your email and password ==");
            System.out.print("Email: ");
            email = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();
            isValid = CheckCredential(email, password);
        }
        System.out.println("Login Successful!!!");

    }

    private static boolean CheckCredential(String email, String password) {
        boolean exists = isUserExists(email);
        if (exists) {
            try {
                Connection conn = DB.connect();
                PreparedStatement preSt = conn.prepareStatement(Query.getPassword);
                preSt.setString(1, email);
                ResultSet result = preSt.executeQuery();
                if (result.next()) {
                    if (result.getString(1).equals(password)) {
                        return true;
                    } else
                        System.out.println("Wrong Credential!\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Sorry, user not exists.\n");
        return false;
    }

    private static boolean isUserExists(String userEmail) {
        Connection conn = DB.connect();
        PreparedStatement preSt;
        try {
            preSt = conn.prepareStatement(Query.userExists);
            preSt.setString(1, userEmail);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void register() {
        while (true) {
            boolean isValid;
            System.out.println("== Please fill in the form ==");

            System.out.print("Name: ");
            name = input.nextLine();
            isValid = Validation.name(name);
            if (!isValid) {
                System.out.println("Name should be only alphanumeric\n");
                continue;
            }

            System.out.print("Email: ");
            email = input.nextLine();
            isValid = Validation.email(email);
            if (!isValid) {
                System.out.println("Invalid Email\n");
                continue;
            }
            isValid = isUserExists(email);
            if (isValid) {
                System.out.println("User already exists!\n");
                break;
            }

            System.out.print("Password: ");
            password = input.nextLine();
            isValid = Validation.password(password);
            if (!isValid) {
                System.out.println(
                        "Password should contains:\n1. At least 6 character\n2. At least one letter, one special character and one number\n");
                continue;
            }

            System.out.print("Confirm Password: ");
            confirmPassword = input.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("Password not matched!\n");
                continue;
            }

            boolean isSuccessfull = UserActions.setUser(name, email, password);
            if (isSuccessfull) {
                System.out.println("Register Successfull!!!\n");
                break;
            } else {
                System.out.println("Someting went wrong!\n");
            }
        }
    }

    private static boolean setUser(String name, String email, String password) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.addUser);
            preSt.setString(1, name);
            preSt.setString(2, email);
            preSt.setString(3, password);
            preSt.executeUpdate();

            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

}
