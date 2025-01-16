package test.whitebox;

import main.profilemanagment.Profile;
import main.supportcenter.SupportCentreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileWhiteBoxTestCopy {

    private SupportCentreManager manager;

    @BeforeEach
    public void setUp() {
        // Instantiate the SupportCentreManager
        manager = new SupportCentreManager();
    }

    @Test
    public void testManagerInstantiation() {
        // Check that the manager is instantiated successfully
        assertNotNull(manager, "SupportCentreManager should be instantiated");
        System.out.println("testManagerInstantiation passed.");
    }

    @Test
    public void testGenerateVerificationCode() {
        Profile profile = new Profile("user", "pass", "email@example.com", "backup@example.com");
        String code = profile.getVerificationCode();
        assertEquals(6, code.length());
        assertTrue(code.matches("\\d{6}"));
    }
}
