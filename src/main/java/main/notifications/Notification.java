package main.notifications;


public class Notification {
    // Unique ID for the notification
    private String id;
    // The content/message of the notification
    private String message;
    // Status of the notification (Read/Unread)
    private boolean isRead;

    // Constructor to initialize a notification with an ID and message
    public Notification(String id, String message) {
        this.id = id;
        this.message = message;
        this.isRead = false; // Default status is Unread
    }

    // Getter for the notification ID
    public String getId() {
        return id;
    }

    // Getter for the notification message
    public String getMessage() {
        return message;
    }

    // Getter to check if the notification is read
    public boolean isRead() {
        return isRead;
    }

    // Mark the notification as read
    public void markAsRead() {
        this.isRead = true;
    }

    // Convert the notification to a CSV-friendly format
    public String toCSV() {
        return String.format("%s | %s | %s", id, message, isRead ? "Read" : "Unread");
    }

    // Create a notification object from a CSV line
    public static Notification fromCSV(String csvLine) {
        String[] parts = csvLine.split(" \\| "); // Split the line into parts
        Notification notification = new Notification(parts[0], parts[1]); // Initialize notification
        if (parts[2].equals("Read")) { // Check if the status is Read
            notification.markAsRead();
        }
        return notification;
    }
}
