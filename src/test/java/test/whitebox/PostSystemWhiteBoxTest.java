package test.whitebox;

import org.junit.jupiter.api.*;
import main.posts.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Post System White Box Tests")
class PostSystemWhiteBoxTest {
    private Posts posts;
    private ViewPosts viewPosts;

    @BeforeEach
    void setUp() {
        posts = new Posts();
        viewPosts = new ViewPosts(posts);
    }

    @Test
    @DisplayName("Test post privacy with different users")
    void testPostPrivacyEdgeCases() {
        // Test case 1: Author viewing their private post
        int postId1 = posts.createPost("Test post", "user1");
        Posts.Post post1 = posts.getPost(postId1, "user1");
        post1.setPrivate(true);
        assertNotNull(posts.getPost(postId1, "user1"), "Author should see their private post");

        // Test case 2: Other user viewing private post
        assertNull(posts.getPost(postId1, "user2"), "Other user should not see private post");

        // Test case 3: Public post visibility
        int postId2 = posts.createPost("Public post", "user1");
        assertNotNull(posts.getPost(postId2, "user2"), "Anyone should see public post");
    }

    @Nested
    @DisplayName("Post Creation Tests")
    class PostCreationTests {
        @Test
        @DisplayName("Test post creation and privacy branches")
        void testPostCreationBranches() {
            int postId = posts.createPost("Test content", "user1");
            Posts.Post post = posts.getPost(postId, "user1");
            
            assertAll("Post privacy transitions",
                () -> {
                    post.setPrivate(true);
                    assertTrue(post.isPrivate(), "Post should be private");
                },
                () -> {
                    post.setPrivate(false);
                    assertFalse(post.isPrivate(), "Post should be public");
                }
            );
        }
    }

    @Nested
    @DisplayName("Like System Tests")
    class LikeSystemTests {
        @Test
        @DisplayName("Test like/unlike branches")
        void testLikeUnlikeBranches() {
            int postId = posts.createPost("Test post", "user1");
            
            assertAll("Like/Unlike operations",
                () -> assertTrue(posts.likePost(postId, "user2"), "Should be able to like"),
                () -> {
                    Posts.Post post = posts.getPost(postId, "user2");
                    assertEquals(1, post.getLikes(), "Like count should be 1");
                },
                () -> assertTrue(posts.unlikePost(postId, "user2"), "Should be able to unlike"),
                () -> {
                    Posts.Post post = posts.getPost(postId, "user2");
                    assertEquals(0, post.getLikes(), "Like count should be 0");
                }
            );
        }
    }

    @Nested
    @DisplayName("Label Management Tests")
    class LabelManagementTests {
        @Test
        @DisplayName("Test label management branches")
        void testLabelManagementBranches() {
            int postId = posts.createPost("Test post", "user1");
            Posts.Post post = posts.getPost(postId, "user1");
            
            assertAll("Label operations",
                () -> {
                    post.addLabel("test");
                    assertTrue(post.getLabels().contains("test"), "Label should be added");
                },
                () -> {
                    post.addLabel("test"); // Duplicate
                    assertEquals(1, post.getLabels().size(), "Should not add duplicate");
                },
                () -> {
                    assertTrue(post.removeLabel("test"), "Should remove existing label");
                    assertFalse(post.getLabels().contains("test"), "Label should be gone");
                },
                () -> assertFalse(post.removeLabel("nonexistent"), "Should handle non-existent label")
            );
        }
    }

    @Nested
    @DisplayName("Comment System Tests")
    class CommentSystemTests {
        @Test
        @DisplayName("Test comment system branches")
        void testCommentSystemBranches() {
            Comment comment = new Comment(1, "Test comment", "user2");
            
            assertAll("Comment properties",
                () -> assertEquals(1, comment.getId(), "Comment ID should match"),
                () -> assertEquals("Test comment", comment.getContent(), "Content should match"),
                () -> assertEquals("user2", comment.getAuthor(), "Author should match"),
                () -> assertTrue(comment.toString().contains("Test comment"), "ToString should contain content")
            );
        }
    }
} 