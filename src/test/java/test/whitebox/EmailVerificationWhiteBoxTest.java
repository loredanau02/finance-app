package test.whitebox;

import main.profilemanagment.EmailVerification;
import main.profilemanagment.Profile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationWhiteBoxTest {

    @Test
    public void testSendVerificationCode() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        String code = profile.getVerificationCode();
        // Verify that sending the code doesn't throw exceptions
        EmailVerification.sendVerificationCode(profile.getEmail(), code);
    }
}
