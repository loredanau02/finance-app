package test.portfolio;

import main.portfolio.Asset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetTest {
    Float amount = 100.0f;
    Float acquisitionPrice = 20.0f;

    @Test
    public void testAssetConstructor() {
        Asset asset = new Asset(amount, acquisitionPrice);
        assertNotNull(asset);
        assertEquals(amount, asset.GetAmount());
        assertEquals(acquisitionPrice, asset.GetAcquisitionPrice());
    }

    @Test
    public void testAssetSetAmount() {
        Asset asset = new Asset(amount, acquisitionPrice);
        assertNotNull(asset);

        asset.SetAmount(200.0f);

        assertEquals(200.0f, asset.GetAmount());
    }

    @Test
    public void testAssetSetFavourite() {
        Asset asset = new Asset(amount, acquisitionPrice);
        assertNotNull(asset);
        assertFalse(asset.IsFavourite());

        asset.SetFavourite();

        assertTrue(asset.IsFavourite());
    }

    @Test
    public void testAssetUnsetFavourite() {
        Asset asset = new Asset(amount, acquisitionPrice);
        assertNotNull(asset);
        asset.SetFavourite();
        assertTrue(asset.IsFavourite());

        asset.UnsetFavourite();

        assertFalse(asset.IsFavourite());
    }
}
