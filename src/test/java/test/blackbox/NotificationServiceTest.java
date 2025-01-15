package test.blackbox;

import main.notifications.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {
    private NotificationService notificationService;

    @BeforeEach
    public void setup() {
        // Ensure the CSV file used for persistence is cleared before each test to avoid interference between tests.
        File file = new File("notifications.csv");
        if (file.exists()) {
            file.delete();
        }
        notificationService = new NotificationService();
    }

    @Test
    public void testAddNotification() {
        // Verify that a notification is correctly added to the system and all its properties are initialized properly.
        notificationService.addNotification("Welcome message");
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Welcome message", notifications.get(0).getMessage());
        assertFalse(notifications.get(0).isRead());
    }

    @Test
    public void testBroadcastNotification() {
        // Confirm that broadcasting a notification works as expected and ensures the message is added to the list.
        notificationService.broadcastNotification("Broadcast message");
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Broadcast message", notifications.get(0).getMessage());
    }

    @Test
    public void testMarkNotificationAsRead_ValidID() {
        // Test that marking a notification as read updates the corresponding notification's `isRead` status.
        notificationService.addNotification("Mark as read test");
        String notificationId = notificationService.getNotifications().get(0).getId();
        notificationService.markNotificationAsRead(notificationId);
        assertTrue(notificationService.getNotifications().get(0).isRead());
    }

    @Test
    public void testMarkNotificationAsRead_InvalidID() {
        // Test behavior when attempting to mark a notification as read with an invalid ID; should handle gracefully without changes.
        notificationService.markNotificationAsRead("invalid-id");
        assertEquals(0, notificationService.getNotifications().size());
    }

    @Test
    public void testDeleteNotification_ValidID() {
        // Ensure a notification is removed when a valid ID is provided, and the system's state reflects the change.
        notificationService.addNotification("Delete test");
        String notificationId = notificationService.getNotifications().get(0).getId();
        notificationService.deleteNotification(notificationId);
        assertTrue(notificationService.getNotifications().isEmpty());
    }

    @Test
    public void testDeleteNotification_InvalidID() {
        // Verify the system handles an invalid ID without modifying the list of notifications.
        notificationService.deleteNotification("invalid-id");
        assertEquals(0, notificationService.getNotifications().size());
    }

    @Test
    public void testPasswordChangeReminderNotification() {
        // Check if the system generates a proper password change reminder notification.
        notificationService.notifyPasswordChangeReminder();
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Reminder: Please update your account password this quarter.", notifications.get(0).getMessage());
    }

    @Test
    public void testSavingsGoalProgressNotification() {
        // Validate that the notification for savings goal progress is correctly generated with accurate formatting.
        notificationService.notifySavingsGoalProgress("Vacation Fund", 75.0);
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Progress for 'Vacation Fund': 75.00% achieved.", notifications.get(0).getMessage());
    }

    @Test
    public void testSaveAndLoadNotifications() {
        // Ensure the notifications are correctly saved to the file and successfully reloaded to maintain persistence.
        notificationService.addNotification("Persisted Message");
        notificationService.saveNotifications();
        notificationService = new NotificationService();
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Persisted Message", notifications.get(0).getMessage());
    }

    @Test
    public void testSequentialAddAndDeleteNotifications() {
        // Simulate sequential operations to confirm the system's consistency and state management.
        notificationService.addNotification("First");
        notificationService.addNotification("Second");
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(2, notifications.size());
        String notificationId = notifications.get(0).getId();
        notificationService.deleteNotification(notificationId);
        notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Second", notifications.get(0).getMessage());
    }
}
