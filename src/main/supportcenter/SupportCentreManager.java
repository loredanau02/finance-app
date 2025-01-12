package main.supportcentre;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class SupportCentreManager {

    private static final String TICKETS_FILE = "data/support_tickets.csv";

    public String createSupportTicket(String userId, String category, String description) {
        String ticketId = UUID.randomUUID().toString();
        SupportTicket ticket = new SupportTicket(ticketId, userId, category, description);

        saveTicketToFile(ticket);
        return ticketId;
    }

    private void saveTicketToFile(SupportTicket ticket) {
        try (FileWriter writer = new FileWriter(TICKETS_FILE, true)) {
            writer.append(ticket.getTicketId()).append(",")
                  .append(ticket.getUserId()).append(",")
                  .append(ticket.getCategory()).append(",")
                  .append(ticket.getDescription()).append(",")
                  .append(ticket.getStatus()).append(",")
                  .append(ticket.getCreatedAt().toString()).append("\n");
        } catch (IOException e) {
            System.err.println("Error saving ticket to file: " + e.getMessage());
        }
    }
}