package main.supportcenter;

import java.time.LocalDateTime;

public class SupportTicket {
    private String ticketId;
    private String userId;
    private String category;
    private String description;
    private String attachmentPath;
    private String status;
    private LocalDateTime createdAt;

    public SupportTicket(String ticketId, String userId, String category, String description, String attachmentPath) {
        if (userId.isEmpty() || category.isEmpty() || description.isEmpty()) {
            throw new IllegalArgumentException("User ID, Category, and Description cannot be empty.");
        }
        this.ticketId = ticketId;
        this.userId = userId;
        this.category = category;
        this.description = description;
        this.attachmentPath = attachmentPath;
        this.status = "Open";
        this.createdAt = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public String getUserId() { return userId; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getAttachmentPath() { return attachmentPath; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(String status) { this.status = status; }

    public String toCSVString() {
        return String.join(",",
            ticketId,
            userId,
            category,
            description.replace(",", ""),
            attachmentPath,
            status,
            createdAt.toString()
        );
    }
}