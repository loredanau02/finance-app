// javac -d out src/Main.java src/main/profilemanagment/*.java src/main/supportcentre/*.java
// java src/Main.java

import main.profilemanagment.Profile;
import main.profilemanagment.AccountManager;
import main.supportcentre.SupportCentreManager;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountManager accountManager = new AccountManager();
    private static SupportCentreManager supportCentreManager = new SupportCentreManager();
    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    registerAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                case 4:
                    createSupportTicket();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Account Management System ===");
        System.out.println("1. Register new account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("4. Create Support Ticket");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registerAccount() {
        System.out.println("\n=== Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter backup email: ");
        String backupEmail = scanner.nextLine();

        if (accountManager.registerAccount(username, password, email, backupEmail)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists. Please try again.");
        }
    }

    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Profile profile = accountManager.login(username, password);
        if (profile != null) {
            System.out.println("Login successful!");
            System.out.println(profile.toString());
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void createSupportTicket() {
        System.out.println("\n=== Create Support Ticket ===");
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
    
        String ticketId = supportCentreManager.createSupportTicket(userId, category, description);
        System.out.println("Support ticket created with ID: " + ticketId);
    }
    
}
