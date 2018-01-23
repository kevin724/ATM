import java.util.*;
import java.sql.*;

public class ATM {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        menu(input);
    }

    public static void menu(Scanner input) {
        Connection conn = new DbConnection().getConn();
        AccountManager manager = new AccountManager(conn);
        Account account;
        boolean repeat = false;
        do {
            if (newUser(input)) {
                System.out.print("Let's get you registered! Please enter " +
                        "a password you would like to use: ");
                String newUserPass = input.nextLine();
                manager.addAccount(newUserPass);
            }
            System.out.print("Please enter your id: ");
            int id = input.nextInt();
            input.nextLine();
            System.out.print("Please enter your password: ");
            String password = input.nextLine();
            account = manager.getAccount(id, password);
            if (account == null) {
                System.out.println("Could not find match. Try again.");
                repeat = true;
            }
        }while (repeat);
        int userChoice = 0;
        while (userChoice != 4)
        {
            userChoice = options(input);
            switch (userChoice)
            {
                case 1:
                    String balance = String.format("%.2f", account.getBalance());
                    System.out.println("Current balance: $" + balance);
                    break;
                case 2:
                    System.out.print("\nEnter Amount You Wish to Deposit: $ ");
                    double amount = input.nextDouble();
                    account.deposit(amount);
                    manager.updateBalance(account.getId(), account.getBalance());
                    break;
                case 3:
                    System.out.print("\nEnter Amount You Wish to Withdraw: $ ");
                    amount = input.nextDouble();
                    account.withdraw(amount);
                    manager.updateBalance(account.getId(), account.getBalance());
                    break;
                case 4:
                    try {
                        conn.close();

                    } catch (Exception e) {
                        System.err.println("Got an exception! ");
                        System.err.println(e.getMessage());
                    }
                    System.out.print("\nBye!");
                    System.exit(0);
                    break;
            }
        }

    }

    public static int options(Scanner input) {
        int choice;
        do
        {
            System.out.print("\nPlease Choose From the Following Options:"
                    + "\n 1. Display Balance \n 2. Deposit"
                    + "\n 3. Withdraw\n 4. Log Out\n\n");

            choice = input.nextInt();
            input.nextLine();

            if (choice < 1 || choice > 4){
                System.out.println("Not a valid choice. Choose again.");
            }

        }while (choice < 1 || choice > 4);

        return choice;
    }

    public static boolean newUser(Scanner input) {
        int choice;
        do
        {
            System.out.print("Welcome!\nPlease Choose From the Following Options:"
                    + "\n 1. I am a new user. \n 2. I am an existing user\n " +
                    "Your response: ");

            choice = input.nextInt();
            input.nextLine();

            if (choice < 1 || choice > 2){
                System.out.println("Not a valid choice. Choose again.");
            }

        }while (choice < 1 || choice > 2);

        return choice == 1;
    }
}
