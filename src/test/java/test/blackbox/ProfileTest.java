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
}
