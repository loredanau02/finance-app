package test.whitebox;

import main.profilemanagment.AccountManager;
import main.profilemanagment.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountManagerWhiteBoxTest {
    private AccountManager accountManager;

    @BeforeEach
    public void setup() {
        accountManager = new AccountManager();
    }

    @Test
    public void testDeleteNonexistentAccount() {
        assertFalse(accountManager.deleteAccount("nonExistentUser"));
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        accountManager.registerAccount("user1", "pass123", "email@example.com", "backup@example.com");
        Profile profile = accountManager.login("user1", "wrongPass");
        assertNull(profile);
    }
}
