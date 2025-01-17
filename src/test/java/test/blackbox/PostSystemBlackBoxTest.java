package test.blackbox;

import org.junit.jupiter.api.*;
import main.posts.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Post System Black Box Tests")
class PostSystemBlackBoxTest {
    private Posts posts;
    private ViewPosts viewPosts;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        posts = new Posts();
        viewPosts = new ViewPosts(posts);
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Create post with valid input")
    void testCreatePostWithValidInput() {
        String username = "testUser";
        String content = "Test post content";
        
        int postId = posts.createPost(content, username);
        Posts.Post post = posts.getPost(postId, username);
        
        assertAll("Post creation",
            () -> assertNotNull(post, "Post should not be null"),
            () -> assertEquals(content, post.getContent(), "Content should match"),
            () -> assertEquals(username, post.getAuthor(), "Author should match")
        );
    }

    @Test
    @DisplayName("Create posts with various content lengths")
    void testCreatePostWithDifferentLengths() {
        String username = "testUser";
        
        // Test empty content
        int postId1 = posts.createPost("", username);
        assertNotNull(posts.getPost(postId1, username), "Should create post with empty content");
        
        // Test single character
        int postId2 = posts.createPost("A", username);
        assertNotNull(posts.getPost(postId2, username), "Should create post with single character");
        
        // Test normal content
        int postId3 = posts.createPost("Hello World!", username);
        assertNotNull(posts.getPost(postId3, username), "Should create post with normal content");
        
        // Test long content
        String longContent = "A".repeat(1000);
        int postId4 = posts.createPost(longContent, username);
        assertNotNull(posts.getPost(postId4, username), "Should create post with long content");
    }

    @Test
    @DisplayName("Private post visibility test")
    void testPrivatePostVisibility() {
        String author = "author";
        String viewer = "viewer";
        
        int postId = posts.createPost("Private post", author);
        Posts.Post post = posts.getPost(postId, author);
        post.setPrivate(true);
        
        assertAll("Private post visibility",
            () -> assertNotNull(posts.getPost(postId, author), "Author should see their private post"),
            () -> assertNull(posts.getPost(postId, viewer), "Other users should not see private post")
        );
    }

    @Test
    @DisplayName("Comment management test")
    void testCommentManagement() {
        String author = "author";
        int postId = posts.createPost("Test post", author);
        
        simulateInput("1\nauthor\nTest comment\n");
        viewPosts.addComment(new Scanner(System.in));
        
        var comments = viewPosts.getCommentsForPost(postId);
        assertAll("Comment verification",
            () -> assertNotNull(comments, "Comments list should exist"),
            () -> assertEquals(1, comments.size(), "Should have one comment"),
            () -> assertEquals("Test comment", comments.get(0).getContent(), "Comment content should match")
        );
    }

    @Test
    @DisplayName("Test post deletion")
    void testPostDeletion() {
        String username = "testUser";
        int postId = posts.createPost("Test post", username);
        
        // Verify post exists
        assertNotNull(posts.getPost(postId, username), "Post should exist before deletion");
        
        // Delete post
        posts.deletePost(postId, username);
        
        // Verify post is deleted
        assertNull(posts.getPost(postId, username), "Post should be null after deletion");
    }

    @Test
    @DisplayName("Test unauthorized post deletion")
    void testUnauthorizedPostDeletion() {
        String author = "author";
        String otherUser = "hacker";
        int postId = posts.createPost("Test post", author);
        
        assertThrows(IllegalArgumentException.class, 
            () -> posts.deletePost(postId, otherUser),
            "Non-author should not be able to delete post"
        );
    }

    @Test
    @DisplayName("Test post editing")
    void testPostEditing() {
        String username = "testUser";
        int postId = posts.createPost("Original content", username);
        Posts.Post post = posts.getPost(postId, username);
        
        // Edit post
        String newContent = "Updated content";
        post.setContent(newContent);
        
        Posts.Post updatedPost = posts.getPost(postId, username);
        assertEquals(newContent, updatedPost.getContent(), "Post content should be updated");
    }

    @Test
    @DisplayName("Test multiple comments on post")
    void testMultipleComments() {
        String author = "author";
        int postId = posts.createPost("Test post", author);
        
        // Add multiple comments
        simulateInput("1\nauthor\nFirst comment\n");
        viewPosts.addComment(new Scanner(System.in));
        
        simulateInput("1\nauthor\nSecond comment\n");
        viewPosts.addComment(new Scanner(System.in));
        
        var comments = viewPosts.getCommentsForPost(postId);
        assertAll("Multiple comments verification",
            () -> assertEquals(2, comments.size(), "Should have two comments"),
            () -> assertEquals("First comment", comments.get(0).getContent(), "First comment content should match"),
            () -> assertEquals("Second comment", comments.get(1).getContent(), "Second comment content should match")
        );
    }

    @Test
    @DisplayName("Test invalid post retrieval")
    void testInvalidPostRetrieval() {
        String username = "testUser";
        int invalidPostId = 99999;
        
        assertNull(posts.getPost(invalidPostId, username), 
            "Getting non-existent post should return null");
    }

    @Test
    @DisplayName("Test special characters in post content")
    void testSpecialCharactersInPost() {
        String username = "testUser";
        String specialContent = "!@#$%^&*()_+ \n\t\"'";
        
        int postId = posts.createPost(specialContent, username);
        Posts.Post post = posts.getPost(postId, username);
        
        assertEquals(specialContent, post.getContent(), 
            "Post should preserve special characters");
    }

    @Test
    @DisplayName("Test comment on private post")
    void testCommentOnPrivatePost() {
        String author = "author";
        String commenter = "commenter";
        int postId = posts.createPost("Private post", author);
        Posts.Post post = posts.getPost(postId, author);
        post.setPrivate(true);
        
        simulateInput(postId + "\n" + commenter + "\nAttempted comment\n");
        viewPosts.addComment(new Scanner(System.in));
        
        var comments = viewPosts.getCommentsForPost(postId);
        assertTrue(comments.isEmpty(), "Should not allow comments on private posts");
    }

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
} 