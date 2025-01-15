package test.blackbox;

import main.profilemanagment.Profile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileTest {
    
    @Test
    public void testProfileCreation() {
        // Arrange
        String username = "testUser";
        String password = "testPass";
        String email = "test@example.com";
        String backupEmail = "backup@example.com";

        Profile profile = new Profile(username, password, email, backupEmail);

        assertEquals(username, profile.getUsername());
        assertEquals(password, profile.getPassword());
        assertEquals(email, profile.getEmail());
        assertEquals(backupEmail, profile.getBackupEmail());
        assertFalse(profile.isEmailVerified());
        assertTrue(profile.isPublic());
    }

    @Test
    public void testSettersAndGetters() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        profile.setUsername("newUser");
        profile.setPassword("newPass");
        profile.setEmail("newEmail@example.com");
        profile.setBackupEmail("newBackup@example.com");
        profile.setEmailVerified(true);
        profile.setPublic(false);

        assertEquals("newUser", profile.getUsername());
        assertEquals("newPass", profile.getPassword());
        assertEquals("newEmail@example.com", profile.getEmail());
        assertEquals("newBackup@example.com", profile.getBackupEmail());
        assertTrue(profile.isEmailVerified());
        assertFalse(profile.isPublic());
    }

    @Test
    public void testEmailVerification() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        assertFalse(profile.isEmailVerified());
        profile.setEmailVerified(true);
        assertTrue(profile.isEmailVerified());
    }

    @Test
    public void testPublicProfile() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        assertTrue(profile.isPublic());
        profile.setPublic(false);
        assertFalse(profile.isPublic());
    }
}
