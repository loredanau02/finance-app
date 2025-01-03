package test.main.portfolio;

import main.portfolio.Asset;
import main.portfolio.Portfolio;
import main.portfolio.PortfolioManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class PortfolioManagerTest {

    @Test
    public void testGetUserPortfolio_ExistingUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");

        Portfolio portfolio = portfolioManager.GetUserPortfolio("peter");
        assertNotNull(portfolio);
    }

    @Test
    public void testGetUserPortfolio_NonExistingUser() {
        PortfolioManager portfolioManager = new PortfolioManager();

        Portfolio portfolio = portfolioManager.GetUserPortfolio("non_user");
        assertNull(portfolio);
    }

    @Test
    public void testAddAsset_Success() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");

        boolean result = portfolioManager.AddAsset("peter", "apple", 100.0f);
        assertTrue(result);

        Asset asset = portfolioManager.GetAsset("peter", "apple");
        assertNotNull(asset);
        assertEquals(100.0f, asset.GetAmount());
    }

    @Test
    public void testAddAsset_UserDoesNotExist() {
        PortfolioManager portfolioManager = new PortfolioManager();

        boolean result = portfolioManager.AddAsset("bread", "apple", 100.0f);
        assertFalse(result);
    }

    @Test
    public void testAddAsset_AssetAlreadyExists() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");
        portfolioManager.AddAsset("peter", "apple", 100.0f);

        boolean result = portfolioManager.AddAsset("peter", "apple", 150.0f);
        assertFalse(result);

        Asset asset = portfolioManager.GetAsset("peter", "apple");
        assertEquals(100.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetAmount_Success() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");
        portfolioManager.AddAsset("peter", "google", 200.0f);

        boolean result = portfolioManager.UpdateAssetAmount("peter", "google", 250.0f);
        assertTrue(result);

        Asset asset = portfolioManager.GetAsset("peter", "google");
        assertEquals(250.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetAmount_NonExistingAsset() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");

        boolean result = portfolioManager.UpdateAssetAmount("peter", "microsoft", 300.0f);
        assertFalse(result);

        Asset asset = portfolioManager.GetAsset("peter", "microsoft");
        assertNull(asset);
    }

    @Test
    public void testUpdateAssetAmount_UserDoesNotExist() {
        PortfolioManager portfolioManager = new PortfolioManager();

        boolean result = portfolioManager.UpdateAssetAmount("bread", "apple", 100.0f);
        assertFalse(result);
    }

    @Test
    public void testGetAsset_Existing() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");
        portfolioManager.AddAsset("pedro", "google", 50.0f);

        Asset asset = portfolioManager.GetAsset("pedro", "google");
        assertNotNull(asset);
        assertEquals(50.0f, asset.GetAmount());
    }

    @Test
    public void testGetAsset_NonExistingAsset() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");

        Asset asset = portfolioManager.GetAsset("pedro", "Microsoft");
        assertNull(asset);
    }

    @Test
    public void testGetAsset_UserDoesNotExist() {
        PortfolioManager portfolioManager = new PortfolioManager();

        Asset asset = portfolioManager.GetAsset("non_user", "apple");
        assertNull(asset);
    }

    @Test
    public void testGetAssets_ExistingUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("peter");
        portfolioManager.AddAsset("peter", "apple", 10.0f);
        portfolioManager.AddAsset("peter", "google", 20.0f);
        portfolioManager.AddAsset("peter", "microsoft", 5.0f);

        Map<String, Asset> assets = portfolioManager.GetAssets("peter");
        assertNotNull(assets);
        assertEquals(3, assets.size());
        assertTrue(assets.containsKey("apple"));
        assertTrue(assets.containsKey("google"));
        assertTrue(assets.containsKey("microsoft"));
    }

    @Test
    public void testGetAssets_UserDoesNotExist() {
        PortfolioManager portfolioManager = new PortfolioManager();

        Map<String, Asset> assets = portfolioManager.GetAssets("bread");
        assertNull(assets);
    }

    @Test
    public void testRemoveAsset_Success() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");
        portfolioManager.AddAsset("pedro", "google", 50.0f);

        boolean result = portfolioManager.RemoveAsset("pedro", "google");
        assertTrue(result);

        Asset asset = portfolioManager.GetAsset("pedro", "google");
        assertNull(asset);
    }

    @Test
    public void testRemoveAsset_NonExistingAsset() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");

        boolean result = portfolioManager.RemoveAsset("pedro", "microsoft");
        assertFalse(result);
    }

    @Test
    public void testRemoveAsset_UserDoesNotExist() {
        PortfolioManager portfolioManager = new PortfolioManager();

        boolean result = portfolioManager.RemoveAsset("non_user", "apple");
        assertFalse(result);
    }
}
