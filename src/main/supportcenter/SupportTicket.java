package main.supportcentre;

import java.time.LocalDateTime;

public class SupportTicket {
    private String ticketId;
    private String userId;
    private String category;
    private String description;
    private String status;
    private LocalDateTime createdAt;

    public SupportTicket(String ticketId, String userId, String category, String description) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.category = category;
        this.description = description;
        this.status = "Open";
        this.createdAt = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public String getUserId() { return userId; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(String status) { this.status = status; }
}