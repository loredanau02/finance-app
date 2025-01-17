package main.supportcenter;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class SupportCentreManager {

    private static final String TICKETS_FILE       = "src/main/java/main/supportcenter/data/support_tickets.csv";
    private static final String RESPONSES_FILE     = "src/main/java/main/supportcenter/data/ticket_responses.csv";
    private static final String RATINGS_FILE       = "src/main/java/main/supportcenter/data/support_ticket_ratings.csv";
    private static final String REALTIME_CHATS     = "src/main/java/main/supportcenter/data/real_time_chats.csv";

    public String createSupportTicket(String userId, String category, String description, String attachmentPath) {
        String ticketId = UUID.randomUUID().toString();
        SupportTicket ticket = new SupportTicket(ticketId, userId, category, description, attachmentPath);

        saveTicketToFile(ticket);
        return ticketId;
    }


    private void saveTicketToFile(SupportTicket ticket) {
        try {
            Path ticketFilePath = Paths.get(TICKETS_FILE);
            Files.createDirectories(ticketFilePath.getParent());

            try (FileWriter writer = new FileWriter(TICKETS_FILE, true)) {
                writer.append(ticket.getTicketId()).append(",")
                        .append(ticket.getUserId()).append(",")
                        .append(ticket.getCategory()).append(",")
                        .append(ticket.getDescription()).append(",")
                        .append(ticket.getAttachmentPath()).append(",")
                        .append(ticket.getStatus()).append(",")
                        .append(ticket.getCreatedAt().toString())
                        .append("\n");
            } catch (IOException e) {
                System.err.println("Error saving ticket to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
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

    public boolean answerTicket(String ticketId, String message) {
        try (FileWriter writer = new FileWriter(RESPONSES_FILE, true)) {
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
        try (FileWriter writer = new FileWriter(REALTIME_CHATS, true)) {
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
            return false;
        }

        try (FileWriter writer = new FileWriter(RATINGS_FILE, true)) {
            writer.append(ticketId)
                    .append(",")
                    .append(userId)
                    .append(",")
                    .append(String.valueOf(rating))
                    .append(",")
                    .append(feedback)
                    .append("\n");
            return true;
        } catch (IOException e) {
            System.err.println("Error saving ticket rating: " + e.getMessage());
            return false;
        }
    }

    private List<SupportTicket> loadTickets() {
        List<SupportTicket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1);
                if (fields.length < 7 || fields[0].equalsIgnoreCase("TicketID")) {
                    continue;
                }
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
}