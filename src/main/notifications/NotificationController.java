package main.notifications;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class NotificationController {
    private NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nNotification Center Menu:");
            System.out.println("1. View All Notifications");
            System.out.println("2. Add Notification");
            System.out.println("3. Mark Notification as Read");
            System.out.println("4. Delete Notification");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayAllNotifications();
                case 2 -> addNotification();
                case 3 -> markNotificationAsRead();
                case 4 -> deleteNotification();
                case 5 -> {
                    System.out.println("Exiting Notification Center...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public void displayAllNotifications() {
        System.out.println("\nAll Notifications:");
        for (Notification notification : service.getAllNotifications()) {
            System.out.println(notification);
            System.out.println("---------------------------");
        }
    }

    public void addNotification() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter notification message: ");
        String message = scanner.nextLine();
        int id = service.getAllNotifications().size() + 1; // Auto-increment ID
        String timestamp = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").format(new Date());
        Notification notification = new Notification(id, message, false, timestamp);
        service.addNotification(notification);
        System.out.println("Notification added successfully!");
    }

    public void markNotificationAsRead() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter notification ID to mark as read: ");
        int id = scanner.nextInt();
        service.markAsRead(id);
        System.out.println("Notification marked as read!");
    }

    public void deleteNotification() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter notification ID to delete: ");
        int id = scanner.nextInt();
        service.deleteNotification(id);
        System.out.println("Notification deleted successfully!");
    }
}
