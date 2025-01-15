package test.blackbox;

import main.profilemanagment.EmailVerification;
import main.profilemanagment.Profile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailVerificationTest {

    @Test
    public void testSendVerificationCode() {
        // This test would require mocking the email sending functionality.
        // For now, we can just check if the method runs without exceptions.
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        String verificationCode = profile.getVerificationCode();
        EmailVerification.sendVerificationCode(profile.getEmail(), verificationCode);
        // Check console output or use a mocking framework to verify email sending.
    }

    @Test
    public void testVerifyEmail() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        String verificationCode = profile.getVerificationCode();
        assertTrue(EmailVerification.verifyEmail(profile, verificationCode));
        assertTrue(profile.isEmailVerified());
    }

    @Test
    public void testVerifyEmailWithInvalidCode() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        assertFalse(EmailVerification.verifyEmail(profile, "invalidCode"));
        assertFalse(profile.isEmailVerified());
    }
}
