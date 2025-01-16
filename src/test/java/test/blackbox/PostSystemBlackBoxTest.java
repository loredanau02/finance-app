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

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
} 