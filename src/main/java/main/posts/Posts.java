package main.posts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Posts {
    private HashMap<Integer, Post> posts; // Map of postId to Post object
    private int postIdCounter; // Counter for unique post IDs

    public Posts() {
        this.posts = new HashMap<>();
        this.postIdCounter = 1;
    }

    // Create a new post
    public int createPost(String content, String author) {
        int postId = postIdCounter++;
        Post newPost = new Post(postId, content, author);
        posts.put(postId, newPost);
        return postId;
    }

    // Delete a post
public boolean deletePost(int postId, String username) {
    Post post = posts.get(postId);
    if (post == null) {
        throw new IllegalArgumentException("Post not found");
    }
    if (!post.getAuthor().equals(username)) {
        throw new IllegalArgumentException("Unauthorized deletion attempt");
    }
    posts.remove(postId);
    return true;
}


    // Like a post
    public boolean likePost(int postId, String username) {
        Post post = posts.get(postId);
        if (post != null && (!post.isPrivate() || post.getAuthor().equals(username))) {
            post.addLike();
            return true;
        }
        return false;
    }

    // Unlike a post
    public boolean unlikePost(int postId, String username) {
        Post post = posts.get(postId);
        if (post != null && (!post.isPrivate() || post.getAuthor().equals(username))) {
            post.removeLike();
            return true;
        }
        return false;
    }

    // Report a post
    public boolean reportPost(int postId, String reason, String username) {
        Post post = posts.get(postId);
        if (post != null && (!post.isPrivate() || post.getAuthor().equals(username))) {
            post.addReport(reason);
            return true;
        }
        return false;
    }

    // Update a post's content
    public boolean updatePostContent(int postId, String newContent, String username) {
        Post post = posts.get(postId);
        if (post != null && post.getAuthor().equals(username)) {
            post.setContent(newContent);
            return true;
        }
        return false;
    }

    // Retrieve all posts
    public ArrayList<Post> getAllPosts(String viewerUsername) {
        ArrayList<Post> visiblePosts = new ArrayList<>();
        for (Post post : posts.values()) {
            if (!post.isPrivate() || post.getAuthor().equals(viewerUsername)) {
                visiblePosts.add(post);
            }
        }
        return visiblePosts;
    }

    // Retrieve a single post by ID
    public Post getPost(int postId, String viewerUsername) {
        Post post = posts.get(postId);
        if (post != null && (!post.isPrivate() || post.getAuthor().equals(viewerUsername))) {
            return post;
        }
        return null;
    }

    // Inner class for a Post
    public class Post {
        private int id;
        private String content;
        private String author;
        private int likes;
        private ArrayList<String> reports;
        private ArrayList<String> labels;
        private boolean isPrivate;

        public Post(int id, String content, String author) {
            this.id = id;
            this.content = content;
            this.author = author;
            this.likes = 0;
            this.reports = new ArrayList<>();
            this.labels = new ArrayList<>();
            this.isPrivate = false;
        }

        public void addLike() {
            likes++;
        }

        public void removeLike() {
            if (likes > 0) likes--;
        }

        public void addReport(String reason) {
            reports.add(reason);
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getAuthor() {
            return author;
        }

        public int getLikes() {
            return likes;
        }

        public ArrayList<String> getReports() {
            return reports;
        }

        public void addLabel(String label) {
            if (!labels.contains(label)) {
                labels.add(label);
            }
        }

        public boolean removeLabel(String label) {
            return labels.remove(label);
        }

        public ArrayList<String> getLabels() {
            return labels;
        }

        public void setPrivate(boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

        public boolean isPrivate() {
            return isPrivate;
        }
    }

    // Add these new methods to handle terminal interaction
    public void handlePostsMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("\n=== Posts Menu ===");
            System.out.println("1. Create new post");
            System.out.println("2. View all posts");
            System.out.println("3. Like a post");
            System.out.println("4. Unlike a post");
            System.out.println("5. Report a post");
            System.out.println("6. Update post content");
            System.out.println("7. Delete post");
            System.out.println("8. Add label to post");
            System.out.println("9. Remove label from post");
            System.out.println("10. Add comment to post");
            System.out.println("11. Delete comment from post");
            System.out.println("12. View comments on my posts");
            System.out.println("13. Return to main menu");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createPostFromInput(scanner, username);
                    break;
                case "2":
                    displayAllPosts(username);
                    break;
                case "3":
                    likePostFromInput(scanner, username);
                    break;
                case "4":
                    unlikePostFromInput(scanner, username);
                    break;
                case "5":
                    reportPostFromInput(scanner, username);
                    break;
                case "6":
                    updatePostFromInput(scanner, username);
                    break;
                case "7":
                    deletePostFromInput(scanner, username);
                    break;
                case "8":
                    addLabelToPost(scanner, username);
                    break;
                case "9":
                    removeLabelFromPost(scanner, username);
                    break;
                case "10":
                    addCommentToPost(scanner);
                    break;
                case "11":
                    deleteCommentFromPost(scanner);
                    break;
                case "12":
                    ViewPosts viewPosts = new ViewPosts(this);
                    viewPosts.viewCommentsOnUserPosts(username);
                    break;
                case "13":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createPostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();
        
        int postId = createPost(content, username);
        System.out.println("Post created successfully with ID: " + postId);
        
        System.out.print("Would you like to add labels to this post? (y/n): ");
        if (scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            System.out.print("Enter labels (comma-separated): ");
            String[] labels = scanner.nextLine().split(",");
            Post post = getPost(postId, username);
            for (String label : labels) {
                post.addLabel(label.trim().toLowerCase());
            }
            System.out.println("Labels added successfully!");
        }
    }

    private void displayAllPosts(String username) {
        ArrayList<Post> allPosts = getAllPosts(username);
        if (allPosts.isEmpty()) {
            System.out.println("No posts available.");
            return;
        }
        
        for (Post post : allPosts) {
            displayPost(post);
        }
    }

    private void displayPost(Post post) {
        System.out.println("\n------------------");
        System.out.println("Post ID: " + post.getId());
        System.out.println("Author: " + post.getAuthor());
        System.out.println("Content: " + post.getContent());
        System.out.println("Likes: " + post.getLikes());
        System.out.println("Labels: " + String.join(", ", post.getLabels()));
        System.out.println("Privacy: " + (post.isPrivate() ? "Private" : "Public"));
        if (!post.getReports().isEmpty()) {
            System.out.println("Reports: " + post.getReports().size());
        }
        System.out.println("------------------");
    }

    private void likePostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post ID to like: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            if (likePost(postId, username)) {
                System.out.println("Post liked successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to like it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void unlikePostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post ID to unlike: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            if (unlikePost(postId, username)) {
                System.out.println("Post unliked successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to unlike it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void reportPostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post ID to report: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter reason for report: ");
            String reason = scanner.nextLine();
            if (reportPost(postId, reason, username)) {
                System.out.println("Post reported successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to report it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void updatePostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post ID to update: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new content: ");
            String newContent = scanner.nextLine();
            if (updatePostContent(postId, newContent, username)) {
                System.out.println("Post updated successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to update it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void deletePostFromInput(Scanner scanner, String username) {
        System.out.print("Enter post ID to delete: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            if (deletePost(postId, username)) {
                System.out.println("Post deleted successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to delete it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void addLabelToPost(Scanner scanner, String username) {
        System.out.print("Enter post ID: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            Post post = getPost(postId, username);
            
            if (post != null) {
                System.out.print("Enter label to add: ");
                String label = scanner.nextLine().trim().toLowerCase();
                post.addLabel(label);
                System.out.println("Label added successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to modify it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void removeLabelFromPost(Scanner scanner, String username) {
        System.out.print("Enter post ID: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            Post post = getPost(postId, username);
            
            if (post != null) {
                if (post.getLabels().isEmpty()) {
                    System.out.println("This post has no labels.");
                    return;
                }
                
                System.out.println("Current labels: " + String.join(", ", post.getLabels()));
                System.out.print("Enter label to remove: ");
                String label = scanner.nextLine().trim().toLowerCase();
                
                if (post.removeLabel(label)) {
                    System.out.println("Label removed successfully!");
                } else {
                    System.out.println("Label not found on this post.");
                }
            } else {
                System.out.println("Post not found or you don't have permission to modify it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void addCommentToPost(Scanner scanner) {
        ViewPosts viewPosts = new ViewPosts(this);
        viewPosts.addComment(scanner);
    }

    private void deleteCommentFromPost(Scanner scanner) {
        ViewPosts viewPosts = new ViewPosts(this);
        viewPosts.deleteComment(scanner);
    }
}