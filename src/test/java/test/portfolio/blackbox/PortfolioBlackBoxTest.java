package test.portfolio.blackbox;

import main.portfolio.Asset;
import main.portfolio.Portfolio;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioBlackBoxTest {

    @Test
    public void testAddAsset() {
        Portfolio portfolio = new Portfolio();
        boolean result = portfolio.AddAsset("GOOGL", 10.0f, 20.0f);
        assertTrue(result);

        Asset asset = portfolio.GetAsset("GOOGL");
        assertNotNull(asset);
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testAddExistingAsset() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("GOOGL", 10.0f, 20.0f);

        boolean result = portfolio.AddAsset("GOOGL", 15.0f, 20.0f);
        assertFalse(result);

        Asset asset = portfolio.GetAsset("GOOGL");
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAsset_Buy() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("GOOGL", 10.0f, 20.0f);

        assertTrue(portfolio.UpdateAsset("GOOGL", 5.0f, "BUY", 25.0f));

        Asset asset = portfolio.GetAsset("GOOGL");
        assertNotNull(asset);
        assertEquals(15.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAsset_Sell() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("GOOGL", 10.0f, 20.0f);

        assertTrue(portfolio.UpdateAsset("GOOGL", 5.0f, "SELL", 0.0f));

        Asset asset = portfolio.GetAsset("GOOGL");
        assertNotNull(asset);
        assertEquals(5.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateNotExistingAsset() {
        Portfolio portfolio = new Portfolio();
        assertFalse(portfolio.UpdateAsset("GOOGL", 10.0f, "BUY", 20.0f));
    }

    @Test
    public void testRemoveAsset() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("AAPL", 100.0f, 20.0f);

        assertTrue(portfolio.RemoveAsset("AAPL"));

        Asset asset = portfolio.GetAsset("AAPL");
        assertNull(asset);
    }

    @Test
    public void testRemoveNonExistingAsset() {
        Portfolio portfolio = new Portfolio();
        assertFalse(portfolio.RemoveAsset("strange_asset"));
    }

    @Test
    public void testGetAsset() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("GOOGL", 10.0f, 20.0f);

        Asset asset = portfolio.GetAsset("GOOGL");
        assertNotNull(asset);
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testGetNonExistingAsset() {
        Portfolio portfolio = new Portfolio();
        Asset asset = portfolio.GetAsset("strange_asset");
        assertNull(asset);
    }

    @Test
    public void testGetAssets() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("AAPL", 10.0f, 20.0f);
        portfolio.AddAsset("GOOGL", 20.0f, 20.0f);
        portfolio.AddAsset("MSFT", 5.0f, 20.0f);

        Map<String, Asset> assets = portfolio.GetAssets();
        assertEquals(3, assets.size());

        assertTrue(assets.containsKey("AAPL"));
        assertTrue(assets.containsKey("GOOGL"));
        assertTrue(assets.containsKey("MSFT"));
    }
}
