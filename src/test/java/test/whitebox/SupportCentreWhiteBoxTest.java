package test.whitebox;

import main.supportcenter.SupportCentreManager;
import main.supportcenter.SupportTicket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SupportCentreWhiteBoxTest {

    private SupportCentreManager manager;
    private static final String TICKETS_FILE = "support_center/data/support_tickets.csv";
    private String originalTicketsContent;

    @Before
    public void setUp() throws IOException {
        manager = new SupportCentreManager();
        createEmptyFile(TICKETS_FILE);
        try (FileWriter fw = new FileWriter(TICKETS_FILE, true)) {
            fw.write("TicketID,UserID,Category,Description,Attachment,Status,CreatedAt\n");
        }
    }

    @After
    public void tearDown() throws IOException {
        restoreFile(TICKETS_FILE, originalTicketsContent);
    }

    @Test
    public void testLoadTickets_InternalMethod() throws Exception {
        try (FileWriter fw = new FileWriter(TICKETS_FILE, true)) {
            fw.write("ABC123,user1,Billing,Need invoice,,Open,2025-01-10T10:00\n");
            fw.write("XYZ456,user2,Technical,Crash error,/path/to/log,Closed,2025-01-11T11:00\n");
        }

        Method loadTicketsMethod = SupportCentreManager.class.getDeclaredMethod("loadTickets");
        loadTicketsMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<SupportTicket> tickets = (List<SupportTicket>) loadTicketsMethod.invoke(manager);

        assertEquals("Should load 2 tickets from file", 2, tickets.size());

        SupportTicket t1 = tickets.get(0);
        assertEquals("Ticket ID mismatch", "ABC123", t1.getTicketId());
        assertEquals("User mismatch",     "user1",   t1.getUserId());
        assertEquals("Category mismatch","Billing",  t1.getCategory());
        assertEquals("Status mismatch",   "Open",    t1.getStatus());

        SupportTicket t2 = tickets.get(1);
        assertEquals("Ticket ID mismatch", "XYZ456", t2.getTicketId());
        assertEquals("User mismatch",      "user2",   t2.getUserId());
        assertEquals("Attachment mismatch","/path/to/log", t2.getAttachmentPath());
        assertEquals("Status mismatch",    "Closed",  t2.getStatus());
    }

    @Test
    public void testSaveAllTickets_InternalMethod() throws Exception {
        SupportTicket ticket1 = new SupportTicket("TID1", "AlphaUser", "Billing", "Need refund", "receipt.png");
        SupportTicket ticket2 = new SupportTicket("TID2", "BetaUser",  "Technical", "Crash log", "");

        List<SupportTicket> tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        Method saveAllMethod = SupportCentreManager.class.getDeclaredMethod("saveAllTickets", List.class);
        saveAllMethod.setAccessible(true);

        saveAllMethod.invoke(manager, tickets);

        String fileContent = new String(Files.readAllBytes(Paths.get(TICKETS_FILE)));
        assertTrue("Should contain TID1 data", fileContent.contains("TID1,AlphaUser,Billing,Need refund,receipt.png,Open"));
        assertTrue("Should contain TID2 data", fileContent.contains("TID2,BetaUser,Technical,Crash log,,Open"));
    }

    @Test
    public void testUpdateTicketStatus_TicketNotFoundBranch() throws IOException {
        boolean updated = manager.updateTicketStatus("NonExistent", "Resolved");
        assertFalse("Expected false if the ticket does not exist", updated);
    }

    @Test
    public void testRateTicket_InvalidRatingBranch() {
        String ticketId = manager.createSupportTicket("userX", "General", "Test rating", null);
        boolean result = manager.rateTicket(ticketId, "userX", 6, "Too high");
        assertFalse("Rating above 5 should fail", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSupportTicketConstructor_ThrowsOnEmptyFields() {
        new SupportTicket("TID3", "", "SomeCategory", "SomeDescription", null);
    }

    @Test
    public void testStartRealTimeAid_InternalLogic() {
        boolean started = manager.startRealTimeAid("supportUser", "endUser");
        assertTrue("Current code always returns true", started);
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