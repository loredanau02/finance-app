package test.portfolio;

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
        assertFalse(asset.IsFavourite());
    }

    @Test
    public void testAssetSetAmount() {
        Asset asset = new Asset(100.0f);
        assertNotNull(asset);

        asset.SetAmount(200.0f);

        assertEquals(200.0f, asset.GetAmount());
    }

    @Test
    public void testAssetSetFavourite() {
        Asset asset = new Asset(100.0f);
        assertNotNull(asset);
        assertFalse(asset.IsFavourite());

        asset.SetFavourite();

        assertTrue(asset.IsFavourite());
    }

    @Test
    public void testAssetUnsetFavourite() {
        Asset asset = new Asset(100.0f);
        assertNotNull(asset);
        asset.SetFavourite();
        assertTrue(asset.IsFavourite());

        asset.UnsetFavourite();

        assertFalse(asset.IsFavourite());
    }
}
