package main.supportcenter;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
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

    public void viewAllTickets() {
        System.out.println("\n=== Support Tickets ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE))) {
            String line;
            System.out.printf("%-15s %-10s %-15s %-30s %-10s %-20s%n",
                "Ticket ID", "User ID", "Category", "Description", "Status", "Created At");
            System.out.println("---------------------------------------------------------------------------------------------");
    
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.printf("%-15s %-10s %-15s %-30s %-10s %-20s%n",
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
            }
        } catch (IOException e) {
            System.err.println("Error reading tickets: " + e.getMessage());
        }
    }
}