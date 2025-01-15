package test.blackbox;

import main.profilemanagment.AccountManager;
import main.profilemanagment.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerTest {
    private AccountManager accountManager;

    @BeforeEach
    public void setup() {
        accountManager = new AccountManager();
    }

    @Test
    public void testRegisterAccount() {
        assertTrue(accountManager.registerAccount("testUser", "testPass", "test@example.com", "backup@example.com"));
        assertFalse(accountManager.registerAccount("testUser", "testPass", "test@example.com", "backup@example.com")); // Duplicate username
    }

    @Test
    public void testLogin() {
        accountManager.registerAccount("testUser", "testPass", "test@example.com", "backup@example.com");
        Profile profile = accountManager.login("testUser", "testPass");
        assertNotNull(profile);
        assertEquals("testUser", profile.getUsername());
        
        Profile failedLogin = accountManager.login("testUser", "wrongPass");
        assertNull(failedLogin);
    }

    @Test
    public void testDeleteAccount() {
        accountManager.registerAccount("testUser", "testPass", "test@example.com", "backup@example.com");
        assertTrue(accountManager.deleteAccount("testUser"));
        assertFalse(accountManager.deleteAccount("nonExistentUser")); // Non-existent user
    }

    @Test
    public void testGetProfile() {
        accountManager.registerAccount("testUser", "testPass", "test@example.com", "backup@example.com");
        Profile profile = accountManager.getProfile("testUser");
        assertNotNull(profile);
        assertEquals("testUser", profile.getUsername());
    }
}
