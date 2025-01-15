package main.notifications;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    // List to hold all notifications
    private List<Notification> notifications = new ArrayList<>();
    private static final String FILE_NAME = "notifications.csv"; // File to store notifications

    // Constructor to load notifications from the file when the service starts
    public NotificationService() {
        loadNotifications();
    }

    // Method to add a new notification
    public void addNotification(String message) {
        String id = java.util.UUID.randomUUID().toString(); // Generate unique ID for the notification
        notifications.add(new Notification(id, message));
        saveNotifications(); // Save the updated list of notifications
    }

    // Method to broadcast a notification to all users
    public void broadcastNotification(String message) {
        addNotification(message);
        System.out.println("Broadcasted notification: " + message);
    }

    // Method to mark a notification as read
    public void markNotificationAsRead(String notificationId) {
        for (Notification notification : notifications) {
            if (notification.getId().equals(notificationId)) { // Find the notification by ID
                notification.markAsRead(); // Mark it as read
                saveNotifications(); // Save the updated list
                System.out.println("Notification marked as read: " + notification.getMessage());
                return;
            }
        }
        System.out.println("Notification not found."); // Notify if ID is invalid
    }

    // Method to delete a notification
    public void deleteNotification(String notificationId) {
        notifications.removeIf(notification -> notification.getId().equals(notificationId)); // Remove by ID
        saveNotifications(); // Save the updated list
        System.out.println("Notification deleted.");
    }

    // Method to get the list of notifications
    public List<Notification> getNotifications() {
        return notifications;
    }

    // Save the notifications to a CSV file
    public void saveNotifications() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("ID | Message | Status"); // Write the header
            writer.newLine();
            for (Notification notification : notifications) {
                writer.write(notification.toCSV()); // Write each notification in CSV format
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving notifications: " + e.getMessage());
        }
    }

    // Load notifications from the CSV file
    protected void loadNotifications() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // If the file doesn’t exist, nothing to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                notifications.add(Notification.fromCSV(line)); // Convert CSV lines to notifications
            }
        } catch (IOException e) {
            System.out.println("Error loading notifications: " + e.getMessage());
        }
    }

    // Notify the user to change their password periodically
    public void notifyPasswordChangeReminder() {
        addNotification("Reminder: Please update your account password this quarter.");
        System.out.println("Password change reminder notification added.");
    }

    // Notify the user about their savings goal progress
    public void notifySavingsGoalProgress(String goalName, double progress) {
        addNotification(String.format("Progress for '%s': %.2f%% achieved.", goalName, progress));
        System.out.println("Savings goal progress notification added for goal: " + goalName);
    }
}
