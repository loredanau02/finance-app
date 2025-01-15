package main.posts;

public class Comment {
    private int id;
    private String content;
    private String author;

    public Comment(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s - by %s", id, content, author);
    }
} 