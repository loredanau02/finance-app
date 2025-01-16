package test.portfolio;


import main.portfolio.Portfolio;
import main.portfolio.Asset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioWhiteBoxTest {

    @Test
    public void testUpdateAssetOfNonExistingAsset() {
        Portfolio portfolio = new Portfolio();
        assertFalse(portfolio.UpdateAsset("strange_usser", 10.0f, "BUY", 20.0f));
    }

    @Test
    public void testUpdateAssetWithInvalidSide() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("GOOGL", 10.0f, 20.0f);

        assertFalse(portfolio.UpdateAsset("GOOGL", 5.0f, "strange_side", 10.0f));

        Asset asset = portfolio.GetAsset("GOOGL");
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetWithInvalidInputs() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("AAPL", 10.0f, 20.0f);

        // Zero Amount
        assertFalse(portfolio.UpdateAsset("AAPL", 0.0f, "BUY", 10.0f));
        // Negative Amount
        assertFalse(portfolio.UpdateAsset("AAPL", -5.0f, "BUY", 10.0f));
        // Negative Price
        assertFalse(portfolio.UpdateAsset("AAPL", 5.0f, "BUY", -10.0f));

        Asset asset = portfolio.GetAsset("AAPL");
        assertEquals(10.0f, asset.GetAmount());
    }

    @Test
    public void testUpdateAssetMultipleActions() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("AAPL", 10.0f, 20.0f);

        assertTrue(portfolio.UpdateAsset("AAPL", 5.0f, "BUY", 20.0f));

        Asset asset = portfolio.GetAsset("AAPL");
        assertEquals(15.0f, asset.GetAmount());

        assertFalse(portfolio.UpdateAsset("AAPL", 30.0f, "SELL", 0.0f));

        assertEquals(15.0f, asset.GetAmount());
    }

    @Test
    public void testRemoveAssetFromEmptyPortfolio() {
        Portfolio portfolio = new Portfolio();
        boolean result = portfolio.RemoveAsset("AAPL");
        assertFalse(result);
    }

    @Test
    public void testRemoveAssetTwice() {
        Portfolio portfolio = new Portfolio();
        portfolio.AddAsset("MSFT", 5.0f, 30.0f);

        assertTrue(portfolio.RemoveAsset("MSFT"));
        assertFalse(portfolio.RemoveAsset("MSFT"));
    }
}
