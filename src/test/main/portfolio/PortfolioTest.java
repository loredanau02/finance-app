package test.main.portfolio;

import main.portfolio.Asset;
import main.portfolio.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class PortfolioTest {
    @Test
    public void testAddAsset_Success() {
        Portfolio portfolio = new Portfolio();

        boolean result = portfolio.AddAsset("google", 10.0f);
        assertTrue(result);

        Asset asset = portfolio.GetAsset("google");
        assertNotNull(asset);
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testAddAsset_AddExistingAsset() {
        Portfolio portfolio = new Portfolio();

        portfolio.AddAsset("google", 10.0f);
        boolean result = portfolio.AddAsset("google", 15.0f);
        assertFalse(result);

        Asset asset = portfolio.GetAsset("google");
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetAmount_Success() {
        Portfolio portfolio = new Portfolio();

        portfolio.AddAsset("google", 10.0f);
        boolean result = portfolio.UpdateAssetAmount("google", 15.0f);
        assertTrue(result);

        Asset asset = portfolio.GetAsset("google");
        assertEquals(15.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetAmount_AssetDoesNotExist() {
        Portfolio portfolio = new Portfolio();

        boolean result = portfolio.UpdateAssetAmount("google", 50.0f);
        assertFalse(result);
    }

    @Test
    public void testGetAsset_Existing() {
        Portfolio portfolio = new Portfolio();

        portfolio.AddAsset("google", 10.0f);
        Asset asset = portfolio.GetAsset("google");

        assertNotNull(asset);
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testGetAsset_NonExisting() {
        Portfolio portfolio = new Portfolio();

        Asset asset = portfolio.GetAsset("NotPresentAsset");
        assertNull(asset);
    }

    @Test
    public void testGetAssets() {
        Portfolio portfolio = new Portfolio();

        portfolio.AddAsset("apple", 10.0f);
        portfolio.AddAsset("google", 20.0f);
        portfolio.AddAsset("microsoft", 5.0f);

        Map<String, Asset> assets = portfolio.GetAssets();

        assertEquals(3, assets.size());

        assertTrue(assets.containsKey("apple"));
        assertTrue(assets.containsKey("google"));
        assertTrue(assets.containsKey("microsoft"));

        assertEquals(10.0f, assets.get("apple").GetAmount());
        assertEquals(20.0f, assets.get("google").GetAmount());
        assertEquals(5, assets.get("microsoft").GetAmount());
    }

    @Test
    public void testRemoveAsset_Success() {
        Portfolio portfolio = new Portfolio();

        portfolio.AddAsset("apple", 100.0f);
        boolean result = portfolio.RemoveAsset("apple");
        assertTrue(result);

        Asset asset = portfolio.GetAsset("apple");
        assertNull(asset);
    }

    @Test
    public void testRemoveAsset_NonExisting() {
        Portfolio portfolio = new Portfolio();

        boolean result = portfolio.RemoveAsset("microsoft");
        assertFalse(result);
    }
}
