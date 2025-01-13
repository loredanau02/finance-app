package main.notification;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class NotificationServiceTest {
    private NotificationService notificationService;

    @Before
    public void setup() {
        // Reset the persistence file and reinitialize the service to ensure isolated test environments.
        File file = new File("notifications.csv");
        if (file.exists()) {
            file.delete();
        }
        notificationService = new NotificationService();
    }

    @Test
    public void testAddNotification() {
        // Check if the notification is added with correct initialization, including `isRead` defaulting to `false`.
        notificationService.addNotification("Test Message");
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Test Message", notifications.get(0).getMessage());
        assertFalse(notifications.get(0).isRead());
    }

    @Test
    public void testBroadcastNotification() {
        // Verify that the broadcast functionality works and the notification is added as expected.
        notificationService.broadcastNotification("Broadcast Message");
        List<Notification> notifications = notificationService.getNotifications();
        assertEquals(1, notifications.size());
        assertEquals("Broadcast Message", notifications.get(0).getMessage());
    }

    @Test
    public void testMarkNotificationAsRead() {
        // Test marking a notification as read by verifying the updated `isRead` status.
        notificationService.addNotification("Test Message");
        String notificationId = notificationService.getNotifications().get(0).getId();
        notificationService.markNotificationAsRead(notificationId);
        assertTrue(notificationService.getNotifications().get(0).isRead());
    }

    @Test
    public void testDeleteNotification() {
        // Ensure the notification is removed from the system when deleted with a valid ID.
        notificationService.addNotification("Test Message");
        String notificationId = notificationService.getNotifications().get(0).getId();
        notificationService.deleteNotification(notificationId);
        assertTrue(notificationService.getNotifications().isEmpty());
    }

    @Test
    public void testSaveAndLoadNotifications() {
        // Test the save and load functionality to confirm notifications persist across system restarts.
        notificationService.addNotification("Persisted Message");
        notificationService.saveNotifications();

        notificationService = new NotificationService();
        List<Notification> notifications = notificationService.getNotifications();

        assertEquals(1, notifications.size());
        assertEquals("Persisted Message", notifications.get(0).getMessage());
    }
}
