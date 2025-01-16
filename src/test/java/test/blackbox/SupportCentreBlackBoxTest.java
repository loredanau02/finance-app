package test.blackbox;

import main.supportcenter.SupportCentreManager;
import main.supportcenter.SupportTicket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SupportCentreBlackBoxTest {

    private SupportCentreManager supportCentreManager;
    private final String TICKETS_FILE = "src/main/java/main/supportcenter/data/support_tickets.csv";
    private final String RESPONSES_FILE = "src/main/java/main/supportcenter/data/ticket_responses.csv";
    private final String RATINGS_FILE = "src/main/java/main/supportcenter/data/support_ticket_ratings.csv";
    
    private String backupTickets;
    private String backupResponses;
    private String backupRatings;

    @Before
    public void setUp() throws IOException {
        supportCentreManager = new SupportCentreManager();
        
        backupTickets = backupFile(TICKETS_FILE);
        backupResponses = backupFile(RESPONSES_FILE);
        backupRatings = backupFile(RATINGS_FILE);

        createEmptyFile(TICKETS_FILE);
        createEmptyFile(RESPONSES_FILE);
        createEmptyFile(RATINGS_FILE);
        
        try (FileWriter fw = new FileWriter(TICKETS_FILE, true)) {
            fw.write("TicketID,UserID,Category,Description,Attachment,Status,CreatedAt\n");
        }
    }

    @After
    public void tearDown() throws IOException {
        restoreFile(TICKETS_FILE, backupTickets);
        restoreFile(RESPONSES_FILE, backupResponses);
        restoreFile(RATINGS_FILE, backupRatings);
    }

    @Test
    public void testCreateSupportTicket_Success() throws IOException {
        String userId = "user123";
        String category = "Technical";
        String description = "App keeps crashing";
        String attachmentPath = "C:\\screenshots\\error.png";

        String ticketId = supportCentreManager.createSupportTicket(
                userId, category, description, attachmentPath);
        assertNotNull("Ticket ID should be generated", ticketId);
        
        String fileContent = new String(Files.readAllBytes(Paths.get(TICKETS_FILE)));
        assertTrue("Tickets file should contain the new ticketId", fileContent.contains(ticketId));
        assertTrue("Tickets file should contain userId", fileContent.contains(userId));
        assertTrue("Tickets file should contain category", fileContent.contains(category));
        assertTrue("Tickets file should contain description", fileContent.contains(description));
        assertTrue("Tickets file should contain attachment", fileContent.contains(attachmentPath));
    }

    @Test
    public void testCreateSupportTicket_EmptyUserID() throws IOException {
        String userId = "";  // empty
        String category = "Billing";
        String description = "Payment not processed";
        String attachmentPath = "C:\\receipts\\receipt.png";

        try {
            supportCentreManager.createSupportTicket(userId, category, description, attachmentPath);
            fail("Should have thrown an exception or handled empty userId gracefully.");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("cannot be empty"));
        }
    }

    @Test
    public void testUpdateTicketStatus_Success() throws IOException {
        String ticketId = supportCentreManager.createSupportTicket(
            "userABC", "CategoryA", "Initial description", "");

        boolean updated = supportCentreManager.updateTicketStatus(ticketId, "In Progress");
        assertTrue("Status should be updated", updated);

        String fileContent = new String(Files.readAllBytes(Paths.get(TICKETS_FILE)));
        assertTrue("Tickets file should now contain 'In Progress'", fileContent.contains("In Progress"));
    }

    @Test
    public void testUpdateTicketStatus_TicketNotFound() {
        boolean updated = supportCentreManager.updateTicketStatus("nonexistent-id", "Closed");
        assertFalse("Should not update since ticket doesn't exist", updated);
    }

    @Test
    public void testAnswerTicket_Success() throws IOException {
        String ticketId = supportCentreManager.createSupportTicket(
            "userXYZ", "General", "Need help", "");

        boolean answered = supportCentreManager.answerTicket(ticketId, "Here is the solution.");
        assertTrue("Should record the response successfully", answered);

        String responsesContent = new String(Files.readAllBytes(Paths.get(RESPONSES_FILE)));
        assertTrue("Responses should contain ticketId", responsesContent.contains(ticketId));
        assertTrue("Responses should contain the message", responsesContent.contains("Here is the solution."));
    }

    @Test
    public void testAnswerTicket_EmptyMessage() {
        String ticketId = supportCentreManager.createSupportTicket(
            "userXYZ", "General", "Issue detail", "");

        boolean answered = supportCentreManager.answerTicket(ticketId, "");
        assertFalse("Should fail to record empty message", answered);
    }

    @Test
    public void testRateTicket_Success() throws IOException {
        String ticketId = supportCentreManager.createSupportTicket(
            "userForRating", "Feedback", "No big issues", "");

        // ticketId,userId,rating,feedback
        boolean rated = supportCentreManager.rateTicket(ticketId, "userForRating", 4, "Great help!");
        assertTrue("Should allow rating 4 with feedback", rated);

        String ratingsContent = new String(Files.readAllBytes(Paths.get(RATINGS_FILE)));
        assertTrue("Ratings should contain ticketId", ratingsContent.contains(ticketId));
        assertTrue("Ratings should contain user ID", ratingsContent.contains("userForRating"));
        assertTrue("Ratings should contain the numeric rating '4'", ratingsContent.contains(",4,"));
        assertTrue("Ratings should contain the feedback", ratingsContent.contains("Great help!"));
    }

    @Test
    public void testRateTicket_InvalidRating() {
        String ticketId = supportCentreManager.createSupportTicket(
            "userBadRating", "General", "Testing invalid rating", "");

        boolean rated = supportCentreManager.rateTicket(ticketId, "userBadRating", 6, "Too high rating");
        assertFalse("Rating of 6 (out of 1-5) should fail", rated);
    }

    @Test
    public void testStartRealTimeAid_ValidUser() {
        boolean started = supportCentreManager.startRealTimeAid("specialistID", "userID");
        assertTrue("Should start real-time aid session", started);
    }

    @Test
    public void testSendMessage() throws IOException {
        supportCentreManager.startRealTimeAid("specialistID", "someUser");
        supportCentreManager.sendMessage("specialistID", "someUser", "Hello there!");

        String chatLog = new String(Files.readAllBytes(Paths.get("src/main/java/main/supportcenter/data/real_time_chats.csv")));
        assertTrue(
            "Chat file should contain 'specialistID,someUser,Hello there!'",
            chatLog.contains("specialistID,someUser,Hello there!")
        );
    }

    private String backupFile(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            return "";
        }
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    private void restoreFile(String filePath, String originalContent) throws IOException {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        if (!originalContent.isEmpty()) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(originalContent);
            }
        }
    }

    private void createEmptyFile(String filePath) throws IOException {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        f.getParentFile().mkdirs();
        f.createNewFile();
    }
}