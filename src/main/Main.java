import main.portfolio.Asset;
import main.portfolio.Portfolio;
import main.portfolio.PortfolioManager;
import main.profilemanagment.Profile;
import main.profilemanagment.AccountManager;
import main.supportcenter.SupportCentreManager;
import main.notifications.Notification;
import main.notifications.NotificationService;
import main.profilemanagment.EmailVerification;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountManager accountManager = new AccountManager();
    private static PortfolioManager portfolioManager = new PortfolioManager();
    private static SupportCentreManager supportCentreManager = new SupportCentreManager();
    private static NotificationService notificationService = new NotificationService();

    private static String sessionUsername;
    private static boolean isLoggedIn = false;

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
                    if (!isLoggedIn) {
                        registerAccount();
                    } else {
                        System.out.println("You are already logged in.");
                    }
                    break;
                case 2:
                    if (!isLoggedIn) {
                        login();
                    } else {
                        System.out.println("You are already logged in.");
                    }
                    break;
                case 3:
                    if (isLoggedIn) {
                        addAsset();
                    } else {
                        System.out.println("Please log in to add assets.");
                    }
                    break;
                case 4:
                    if (isLoggedIn) {
                        getAssets();
                    } else {
                        System.out.println("Please log in to view assets.");
                    }
                    break;
                case 5:
                    if (isLoggedIn) {
                        getAsset();
                    } else {
                        System.out.println("Please log in to get asset details.");
                    }
                    break;
                case 6:
                    if (isLoggedIn) {
                        updateAsset();
                    } else {
                        System.out.println("Please log in to update assets.");
                    }
                    break;
                case 7:
                    if (isLoggedIn) {
                        removeAsset();
                    } else {
                        System.out.println("Please log in to remove assets.");
                    }
                    break;
                case 8:
                    if (isLoggedIn) {
                        createSupportTicket();
                    } else {
                        System.out.println("Please log in to create a support ticket.");
                    }
                    break;
                case 9:
                    if (isLoggedIn) {
                        manageNotifications();
                    } else {
                        System.out.println("Please log in to manage notifications.");
                    }
                    break;
                case 10:
                    if (isLoggedIn) {
                        updateUserInfo();
                    } else {
                        System.out.println("Please log in to update your information.");
                    }
                    break;
                case 11:
                    if (isLoggedIn) {
                        displayUserProfile();
                    } else {
                        System.out.println("Please log in to view your profile.");
                    }
                    break;
                case 12:
                    if (isLoggedIn) {
                        deleteAccount();
                    } else {
                        System.out.println("Please log in to delete your account.");
                    }
                    break;
                case 13:
                    if (isLoggedIn) {
                        verifyEmail();
                    } else {
                        System.out.println("Please log in to verify your email.");
                    }
                    break;
                case 14:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Account Management System ===");
        if (!isLoggedIn) {
            System.out.println("1. Register new account");
            System.out.println("2. Login");
        } else {
            System.out.println("3. Add asset");
            System.out.println("4. Get assets");
            System.out.println("5. Get asset");
            System.out.println("6. Update asset");
            System.out.println("7. Remove asset");
            System.out.println("8. Create Support Ticket");
            System.out.println("9. Manage Notifications");
            System.out.println("10. Update Personal Information");
            System.out.println("11. View Profile");
            System.out.println("12. Delete Account");
            System.out.println("13. Verify Email");
        }
        System.out.println("14. Exit");
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
        
        // Username check
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (accountManager.isUsernameTaken(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }
        
        // Password
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        // Email check
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (accountManager.isEmailTaken(email)) {
            System.out.println("This email is already registered. Please use a different email.");
            return;
        }
        
        // Backup email check
        System.out.print("Enter backup email: ");
        String backupEmail = scanner.nextLine();
        if (accountManager.isBackupEmailTaken(backupEmail)) {
            System.out.println("This backup email is already in use. Please use a different email.");
            return;
        }
        
        // If all checks pass, proceed with registration
        if (accountManager.registerAccount(username, password, email, backupEmail)) {
            System.out.println("Registration successful!");
            System.out.println("You can verify your email after logging in.");
        } else {
            System.out.println("Registration failed. Please try again.");
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
            isLoggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void logout() {
        isLoggedIn = false;
        sessionUsername = null;
        System.out.println("You have been logged out.");
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

    private static void displayUserProfile() {
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile != null) {
            System.out.println("\nUser Profile Details:");
            System.out.printf("Username: %s%n", profile.getUsername());
            System.out.printf("Email: %s%n", profile.getEmail());
            System.out.printf("Backup Email: %s%n", profile.getBackupEmail());
            System.out.printf("Email Verified: %s%n", profile.isEmailVerified());
        } else {
            System.out.println("Profile not found.");
        }
    }

    private static void updateUserInfo() {
        while (true) {
            System.out.println("\n=== Update Personal Information ===");
            System.out.println("1. Update Username");
            System.out.println("2. Update Password");
            System.out.println("3. Update Email");
            System.out.println("4. Update Backup Email");
            System.out.println("5. Exit to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getChoice();
            switch (choice) {
                case 1:
                    updateUsername();
                    break;
                case 2:
                    updatePassword();
                    break;
                case 3:
                    updateEmail();
                    break;
                case 4:
                    updateBackupEmail();
                    break;
                case 5:
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updateUsername() {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        
        // Check if the new username is different from the current one
        if (newUsername.equals(sessionUsername)) {
            System.out.println("New username is same as current username.");
            return;
        }
        
        // Check if the new username is already taken
        if (accountManager.isUsernameTaken(newUsername)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }
        
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile != null) {
            String oldUsername = sessionUsername;
            profile.setUsername(newUsername);
            accountManager.updateProfileInCSV(profile, oldUsername);
            sessionUsername = newUsername; // Update session username
            System.out.println("Username updated successfully.");
        } else {
            System.out.println("Profile not found.");
        }
    }

    private static void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile != null) {
            profile.setPassword(newPassword);
            accountManager.updateProfileInCSV(profile, sessionUsername);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Profile not found.");
        }
    }

    private static void updateEmail() {
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile != null) {
            profile.setEmail(newEmail);
            accountManager.updateProfileInCSV(profile, sessionUsername);
            System.out.println("Email updated successfully.");
        } else {
            System.out.println("Profile not found.");
        }
    }

    private static void updateBackupEmail() {
        System.out.print("Enter new backup email: ");
        String newBackupEmail = scanner.nextLine();
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile != null) {
            profile.setBackupEmail(newBackupEmail);
            accountManager.updateProfileInCSV(profile, sessionUsername);
            System.out.println("Backup email updated successfully.");
        } else {
            System.out.println("Profile not found.");
        }
    }

    private static void deleteAccount() {
        System.out.println("\n=== Delete Account ===");
        System.out.println("Are you sure you want to delete your account? This action cannot be undone.");
        System.out.print("Enter 'yes' to confirm or 'no' to cancel: ");
        String confirmation = scanner.nextLine().toLowerCase();

        if (confirmation.equals("yes")) {
            if (accountManager.deleteAccount(sessionUsername)) {
                System.out.println("Account deleted successfully.");
                isLoggedIn = false;
                sessionUsername = null;
            } else {
                System.out.println("Failed to delete account. Please try again.");
            }
        } else {
            System.out.println("Account deletion cancelled.");
        }
    }

    private static void verifyEmail() {
        Profile profile = accountManager.getProfile(sessionUsername);
        if (profile.isEmailVerified()) {
            System.out.println("Your email is already verified.");
            return;
        }

        System.out.println("\n=== Email Verification ===");
        System.out.println("A verification code has been sent to your email.");
        
        // Send verification code
        EmailVerification.sendVerificationCode(profile.getEmail(), profile.getVerificationCode());
        
        System.out.print("Enter the verification code: ");
        String code = scanner.nextLine();
        
        if (EmailVerification.verifyEmail(profile, code)) {
            accountManager.updateProfileInCSV(profile, sessionUsername);
            System.out.println("Email verified successfully!");
        } else {
            System.out.println("Invalid verification code.");
        }
    }
}
