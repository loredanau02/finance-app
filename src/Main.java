import main.portfolio.Asset;
import main.portfolio.Portfolio;
import main.portfolio.PortfolioManager;
import main.profilemanagment.Profile;
import main.profilemanagment.AccountManager;
import main.supportcentre.SupportCentreManager;

import main.portfolio.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import main.notification.Notification;
import main.notification.NotificationService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountManager accountManager = new AccountManager();
    private static PortfolioManager portfolioManager = new PortfolioManager();
    private static SupportCentreManager supportCentreManager = new SupportCentreManager();
    private static NotificationService notificationService = new NotificationService();

    private static String sessionUsername;

    public static void main(String[] args) {
        // Adding some sample notifications
        notificationService.broadcastNotification("Welcome to the Finance App!");
        notificationService.addNotification("Quick Tip: Diversify your investments.");
        notificationService.addNotification("Your support ticket #1234 is now marked as 'Delivered'.");

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
                    loginGuard(Main::addAsset);
                    break;
                case 4:
                    loginGuard(Main::getAssets);
                    break;
                case 5:
                    loginGuard(Main::getAsset);
                    break;
                case 6:
                    loginGuard(Main::updateAsset);
                    break;
                case 7:
                    loginGuard(Main::removeAsset);
                    break;
                case 8:
                    loginGuard(Main::createSupportTicket);
                    break;
                case 9:
                    manageNotifications();
                    break;
                case 10:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Account Management System ===");
        System.out.println("1. Register new account");
        System.out.println("2. Login");
        System.out.println("3. Add asset");
        System.out.println("4. Get assets");
        System.out.println("5. Get asset");
        System.out.println("6. Update asset");
        System.out.println("7. Remove asset");
        System.out.println("8. Create Support Ticket");
        System.out.println("9. Manage Notifications");
        System.out.println("10. Exit");
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
            sessionUsername = username;
            portfolioManager.AddUser(username);
            System.out.println("Login successful!");
            System.out.println(profile.toString());
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void loginGuard(Runnable action) {
        if (sessionUsername == null) {
            System.out.println("Unauthorized. Please login.");
        } else {
            action.run();
        }
    }

    private static void addAsset() {
        System.out.println("\n=== Add a new asset ===");
        System.out.print("Enter asset name: ");
        String assetName = scanner.nextLine();

        Float assetAmount = null;

        System.out.print("Enter asset amount: ");
        String assetAmountStr = scanner.nextLine().trim();
        try {
            assetAmount = Float.parseFloat(assetAmountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the asset amount.");
        }

        Portfolio portfolio = portfolioManager.GetUserPortfolio(sessionUsername);

        if (portfolio.AddAsset(assetName, assetAmount)) {
            System.out.println("Asset added successfully!");
        } else {
            System.out.println("Asset already exists!");
        }
    }

    private static void getAsset() {
        System.out.println("\n=== Getting an asset from user's portfolio ===");
        System.out.print("Enter asset name: ");
        String assetName = scanner.nextLine();

        Portfolio portfolio = portfolioManager.GetUserPortfolio(sessionUsername);
        Asset asset = portfolio.GetAsset(assetName);
        if (asset != null) {
            System.out.println("\nAsset Details:");
            System.out.printf("%-15s: %s%n", "Asset Name", assetName);
            System.out.printf("%-15s: %.2f%n", "Amount", asset.GetAmount());
        } else {
            System.out.println("Asset not found.");
        }
    }

    private static void updateAsset() {
        System.out.println("\n=== Updating an asset from user's portfolio ===");
        System.out.print("Enter asset name: ");
        String assetName = scanner.nextLine();
        Float assetAmount = null;
        System.out.print("Enter new asset amount: ");
        String assetAmountStr = scanner.nextLine().trim();
        try {
            assetAmount = Float.parseFloat(assetAmountStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a numeric value for the asset amount.");
        }

        Portfolio portfolio = portfolioManager.GetUserPortfolio(sessionUsername);
        if (portfolio.UpdateAssetAmount(assetName, assetAmount)) {
            System.out.println("Asset updated successfully!");
        } else {
            System.out.println("Asset does not exists!");
        }
    }

    private static void removeAsset() {
        System.out.println("\n=== Removing an asset from user's portfolio ===");
        System.out.print("Enter asset name: ");
        String assetName = scanner.nextLine();

        Portfolio portfolio = portfolioManager.GetUserPortfolio(sessionUsername);
        if (portfolio.RemoveAsset(assetName)) {
            System.out.println("Asset removed successfully!");
        } else {
            System.out.println("Asset does not exists!");
        }
    }

    private static void getAssets() {
        System.out.println("\n=== Assets in user's portfolio ===");

        Portfolio portfolio = portfolioManager.GetUserPortfolio(sessionUsername);
        Map<String, Asset> assets = portfolio.GetAssets();
        if (assets != null && !assets.isEmpty()) {
            System.out.printf("%-20s %-10s%n", "Asset Name", "Amount");
            System.out.printf("%-20s %-10s%n", "----------", "------");

            for (Map.Entry<String, Asset> entry : assets.entrySet()) {
                String assetName = entry.getKey();
                Float amount = entry.getValue().GetAmount();
                System.out.printf("%-20s %-10.2f%n", assetName, amount);
            }
        } else {
            System.out.println("Your portfolio is currently empty.");
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

    // Notification centre
    private static void manageNotifications() {
        while (true) {
            System.out.println("\nNotifications:");
            List<Notification> notifications = notificationService.getNotifications();
            if (notifications.isEmpty()) {
                System.out.println("No notifications available.");
                break;
            }

            for (Notification notification : notifications) {
                System.out.println("ID: " + notification.getId() + " | Message: " + notification.getMessage() + " | Status: " + (notification.isRead() ? "Read" : "Unread"));
            }

            System.out.println("\nOptions:");
            System.out.println("1. Mark a notification as read");
            System.out.println("2. Delete a notification");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = getChoice();
            switch (choice) {
                case 1:
                    System.out.print("Enter the ID of the notification to mark as read: ");
                    String markId = scanner.nextLine();
                    notificationService.markNotificationAsRead(markId);
                    break;
                case 2:
                    System.out.print("Enter the ID of the notification to delete: ");
                    String deleteId = scanner.nextLine();
                    notificationService.deleteNotification(deleteId);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
