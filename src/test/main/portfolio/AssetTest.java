package test.main.portfolio;

import main.portfolio.Asset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {
    @Test
    public void testAssetConstructor() {
        Float amount = 100.0f;
        Asset asset = new Asset(amount);

        assertNotNull(asset);
        assertEquals(amount, asset.GetAmount());
    }
}
