package test.whitebox;

import main.profilemanagment.Profile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileWhiteBoxTest {

    @Test
    public void testGenerateVerificationCode() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        String code = profile.getVerificationCode();
        assertEquals(6, code.length());
        assertTrue(code.matches("\\d{6}"));
    }
}
