package main.supportcenter;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupportCentreManager {

    private static final String TICKETS_FILE = "support_center/data/support_tickets.csv";

    public String createSupportTicket(String userId, String category, String description, String attachmentPath) {
        String ticketId = UUID.randomUUID().toString();
        SupportTicket ticket = new SupportTicket(ticketId, userId, category, description, attachmentPath);

        saveTicketToFile(ticket);
        return ticketId;
    }

    private void saveTicketToFile(SupportTicket ticket) {
        try (FileWriter writer = new FileWriter(TICKETS_FILE, true)) {
            writer.append(ticket.getTicketId()).append(",")
                  .append(ticket.getUserId()).append(",")
                  .append(ticket.getCategory()).append(",")
                  .append(ticket.getDescription()).append(",")
                  .append(ticket.getAttachmentPath()).append(",")
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
            System.out.printf("%-15s %-10s %-15s %-30s %-20s %-10s %-20s%n",
                "Ticket ID", "User ID", "Category", "Description", "Attachment", "Status", "Created At");
            System.out.println("------------------------------------------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                System.out.printf("%-15s %-10s %-15s %-30s %-20s %-10s %-20s%n",
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
            }
        } catch (IOException e) {
            System.err.println("Error reading tickets: " + e.getMessage());
        }
    }

      public boolean updateTicketStatus(String ticketId, String newStatus) {
        List<String> tickets = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals(ticketId)) {
                    fields[5] = newStatus;
                    isUpdated = true;
                }
                tickets.add(String.join(",", fields));
            }
        } catch (IOException e) {
            System.err.println("Error reading tickets: " + e.getMessage());
        }

        if (isUpdated) {
            try (FileWriter writer = new FileWriter(TICKETS_FILE)) {
                for (String ticket : tickets) {
                    writer.write(ticket + "\n");
                }
            } catch (IOException e) {
                System.err.println("Error updating ticket: " + e.getMessage());
            }
        }

        return isUpdated;
    }
    
    public boolean answerTicket(String ticketId, String message) {
        String messagesFile = "data/ticket_responses.csv";
        try (FileWriter writer = new FileWriter(messagesFile, true)) {
            writer.append(ticketId).append(",").append(message).append("\n");
            return true;
        } catch (IOException e) {
            System.err.println("Error saving ticket response: " + e.getMessage());
            return false;
        }
    }

    public boolean startRealTimeAid(String supportSpecialistId, String userId) {
        System.out.println("Connecting to " + userId + "...");
        return true;
    }
    
    public void sendMessage(String fromId, String toId, String message) {
        String chatLogFile = "data/real_time_chats.csv";
        try (FileWriter writer = new FileWriter(chatLogFile, true)) {
            writer.append(fromId).append(",").append(toId).append(",").append(message).append("\n");
            System.out.println("Message sent to " + toId + ": " + message);
        } catch (IOException e) {
            System.err.println("Error saving chat message: " + e.getMessage());
        }
    }
    
    public void endRealTimeAid(String supportSpecialistId, String userId) {
        System.out.println("Disconnected from " + userId + ".");
    }

    public boolean rateTicket(String ticketId, String userId, int rating, String feedback) {
        if (rating < 1 || rating > 5) {
            System.err.println("Rating must be between 1 and 5.");
            return false;
        }
    
        String ratingsFile = "data/support_ticket_ratings.csv";
        try (FileWriter writer = new FileWriter(ratingsFile, true)) {
            writer.append(ticketId).append(",")
                  .append(userId).append(",")
                  .append(String.valueOf(rating)).append(",")
                  .append(feedback == null ? "" : feedback).append("\n");
            System.out.println("Thank you for your feedback!");
            return true;
        } catch (IOException e) {
            System.err.println("Error saving rating: " + e.getMessage());
            return false;
        }
    }

    private List<SupportTicket> loadTickets() {
        List<SupportTicket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1); // Handle empty fields
                SupportTicket ticket = new SupportTicket(
                    fields[0], fields[1], fields[2], fields[3], fields[4]
                );
                ticket.setStatus(fields[5]);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            System.err.println("Error reading tickets file: " + e.getMessage());
        }
        return tickets;
    }

    private void saveAllTickets(List<SupportTicket> tickets) {
        try (FileWriter writer = new FileWriter(TICKETS_FILE)) {
            writer.write("TicketID,UserID,Category,Description,Attachment,Status,CreatedAt\n"); // Add header
            for (SupportTicket ticket : tickets) {
                writer.write(ticket.toCSVString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving tickets to file: " + e.getMessage());
        }
    }

    public boolean updateTicketStatus(String ticketId, String newStatus) {
        List<SupportTicket> tickets = loadTickets();
        boolean isUpdated = false;
    
        for (SupportTicket ticket : tickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                ticket.setStatus(newStatus);
                isUpdated = true;
                break;
            }
        }
    
        if (isUpdated) {
            saveAllTickets(tickets);
        }
        return isUpdated;
    }
}