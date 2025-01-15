package main.posts;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import main.posts.Comment;
import java.util.Set;
import java.util.HashSet;

public class ViewPosts {
    private Posts postsManager;
    private HashMap<Integer, ArrayList<Comment>> comments; // Map postId to comments
    private int commentIdCounter;

    public ViewPosts(Posts postsManager) {
        this.postsManager = postsManager;
        this.comments = new HashMap<>();
        this.commentIdCounter = 1;
    }

    public void handleViewMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("\n=== View Posts Menu ===");
            System.out.println("1. View all public posts");
            System.out.println("2. View post by ID");
            System.out.println("3. View posts by label");
            System.out.println("4. View all available labels");
            System.out.println("5. Add comment to post");
            System.out.println("6. Delete comment");
            System.out.println("7. View comments on my posts");
            System.out.println("8. View my private posts");
            System.out.println("9. Toggle post privacy");
            System.out.println("10. Return to main menu");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllPosts(username);
                    break;
                case "2":
                    viewPostById(scanner);
                    break;
                case "3":
                    viewPostsByLabel(scanner, username);
                    break;
                case "4":
                    displayAllLabels(username);
                    break;
                case "5":
                    addComment(scanner);
                    break;
                case "6":
                    deleteComment(scanner);
                    break;
                case "7":
                    viewCommentsOnUserPosts(username);
                    break;
                case "8":
                    viewPrivatePosts(username);
                    break;
                case "9":
                    togglePostPrivacy(scanner, username);
                    break;
                case "10":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewPostsByLabel(Scanner scanner, String username) {
        ArrayList<Posts.Post> allPosts = postsManager.getAllPosts(username);
        Set<String> allLabels = new HashSet<>();
        
        // Collect all unique labels
        for (Posts.Post post : allPosts) {
            allLabels.addAll(post.getLabels());
        }
        
        if (allLabels.isEmpty()) {
            System.out.println("No labels found in any posts.");
            return;
        }
        
        // Display labels as numbered choices
        System.out.println("\n=== Available Labels ===");
        List<String> labelsList = new ArrayList<>(allLabels);
        for (int i = 0; i < labelsList.size(); i++) {
            System.out.println((i + 1) + ". " + labelsList.get(i));
        }
        
        // Get user choice
        System.out.print("\nEnter the number of the label you want to view (1-" + labelsList.size() + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > labelsList.size()) {
                System.out.println("Invalid choice.");
                return;
            }
            
            String selectedLabel = labelsList.get(choice - 1);
            
            // Filter and display posts with the selected label
            List<Posts.Post> filteredPosts = allPosts.stream()
                .filter(post -> post.getLabels().contains(selectedLabel))
                .collect(Collectors.toList());
            
            System.out.println("\n=== Posts with label '" + selectedLabel + "' ===");
            if (filteredPosts.isEmpty()) {
                System.out.println("No posts found with this label.");
                return;
            }
            
            for (Posts.Post post : filteredPosts) {
                displayPost(post);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    public void addComment(Scanner scanner) {
        System.out.print("Enter post ID to comment on: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your username: ");
            String author = scanner.nextLine();
            
            Posts.Post post = postsManager.getPost(postId, author);
            
            if (post != null) {
                System.out.print("Enter your comment: ");
                String content = scanner.nextLine();
                
                Comment newComment = new Comment(commentIdCounter++, content, author);
                comments.computeIfAbsent(postId, k -> new ArrayList<>()).add(newComment);
                System.out.println("Comment added successfully!");
            } else {
                System.out.println("Post not found or you don't have permission to comment on it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    public void deleteComment(Scanner scanner) {
        System.out.print("Enter post ID: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            
            Posts.Post post = postsManager.getPost(postId, username);
            if (post == null) {
                System.out.println("Post not found or you don't have permission to view it.");
                return;
            }
            
            ArrayList<Comment> postComments = comments.get(postId);
            if (postComments != null && !postComments.isEmpty()) {
                System.out.println("\nComments for Post " + postId + ":");
                for (Comment comment : postComments) {
                    System.out.println(comment.toString());
                }
                
                System.out.print("Enter comment ID to delete: ");
                int commentId = Integer.parseInt(scanner.nextLine());
                
                postComments.removeIf(comment -> comment.getId() == commentId);
                System.out.println("Comment deleted successfully!");
            } else {
                System.out.println("No comments found for this post.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void displayAllPosts(String currentUser) {
        ArrayList<Posts.Post> allPosts = postsManager.getAllPosts(currentUser);
        if (allPosts.isEmpty()) {
            System.out.println("No posts available.");
            return;
        }
        
        System.out.println("\n=== All Visible Posts ===");
        for (Posts.Post post : allPosts) {
            displayPost(post);
        }
    }

    private void viewPostById(Scanner scanner) {
        System.out.print("Enter post ID: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            
            Posts.Post post = postsManager.getPost(postId, username);
            
            if (post != null) {
                System.out.println("\n=== Post Details ===");
                displayPost(post);
            } else {
                System.out.println("Post not found or you don't have permission to view it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }

    private void displayPost(Posts.Post post) {
        System.out.println("\n------------------");
        System.out.println("Post ID: " + post.getId());
        System.out.println("Author: " + post.getAuthor());
        System.out.println("Content: " + post.getContent());
        System.out.println("Likes: " + post.getLikes());
        System.out.println("Labels: " + String.join(", ", post.getLabels()));
        System.out.println("Number of Reports: " + post.getReports().size());
        
        if (!post.getReports().isEmpty()) {
            System.out.println("Reports:");
            for (String report : post.getReports()) {
                System.out.println("- " + report);
            }
        }

        // Display comments
        ArrayList<Comment> postComments = comments.get(post.getId());
        if (postComments != null && !postComments.isEmpty()) {
            System.out.println("\nComments:");
            for (Comment comment : postComments) {
                System.out.println(comment.toString());
            }
        }
        
        System.out.println("------------------");
    }

    // Add this new method to allow access to comments
    public ArrayList<Comment> getCommentsForPost(int postId) {
        return comments.get(postId);
    }

    public void viewCommentsOnUserPosts(String author) {
        ArrayList<Posts.Post> allPosts = postsManager.getAllPosts(author);
        boolean foundComments = false;
        
        System.out.println("\n=== Comments on " + author + "'s Posts ===");
        for (Posts.Post post : allPosts) {
            if (post.getAuthor().equals(author)) {
                ArrayList<Comment> postComments = comments.get(post.getId());
                if (postComments != null && !postComments.isEmpty()) {
                    foundComments = true;
                    System.out.println("\nPost ID: " + post.getId());
                    System.out.println("Content: " + post.getContent());
                    System.out.println("Comments:");
                    for (Comment comment : postComments) {
                        System.out.println("- " + comment.toString());
                    }
                    System.out.println("------------------");
                }
            }
        }
        
        if (!foundComments) {
            System.out.println("No comments found on your posts.");
        }
    }

    private void displayAllLabels(String username) {
        ArrayList<Posts.Post> allPosts = postsManager.getAllPosts(username);
        Set<String> allLabels = new HashSet<>();
        
        for (Posts.Post post : allPosts) {
            allLabels.addAll(post.getLabels());
        }
        
        if (allLabels.isEmpty()) {
            System.out.println("No labels found in any posts.");
            return;
        }
        
        System.out.println("\n=== All Available Labels ===");
        int index = 1;
        for (String label : allLabels) {
            System.out.println(index++ + ". " + label);
        }
    }

    private void viewPrivatePosts(String username) {
        ArrayList<Posts.Post> allPosts = postsManager.getAllPosts(username);
        boolean foundPrivatePosts = false;
        
        System.out.println("\n=== My Private Posts ===");
        for (Posts.Post post : allPosts) {
            if (post.isPrivate() && post.getAuthor().equals(username)) {
                displayPost(post);
                foundPrivatePosts = true;
            }
        }
        
        if (!foundPrivatePosts) {
            System.out.println("You have no private posts.");
        }
    }

    private void togglePostPrivacy(Scanner scanner, String username) {
        System.out.print("Enter post ID to toggle privacy: ");
        try {
            int postId = Integer.parseInt(scanner.nextLine());
            Posts.Post post = postsManager.getPost(postId, username);
            
            if (post != null) {
                if (!post.getAuthor().equals(username)) {
                    System.out.println("You can only modify privacy of your own posts.");
                    return;
                }
                
                post.setPrivate(!post.isPrivate());
                System.out.println("Post privacy updated. Post is now " + 
                    (post.isPrivate() ? "private" : "public") + ".");
            } else {
                System.out.println("Post not found or you don't have permission to modify it.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid post ID format.");
        }
    }
}