package test.portfolio;

import main.portfolio.Portfolio;
import main.portfolio.PortfolioManager;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioManagerBlackBoxTest {
    @Test
    public void testGetPrice() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Float price = portfolioManager.GetPrice("AAPL");
        assertNotNull(price);
    }

    @Test
    public void testGetPriceOfNonExistingAsset() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Float price = portfolioManager.GetPrice("strange_asset");
        assertNull(price);
    }

    @Test
    public void testGetPrices() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Float price = portfolioManager.GetPrice("strange_asset");
        assertNull(price);
    }

    @Test
    public void testAddUserAndGetPortfolio() {
        PortfolioManager portfolioManager = new PortfolioManager();
        assertTrue(portfolioManager.AddUser("peter"));

        Portfolio portfolio = portfolioManager.GetUserPortfolio("peter");
        assertNotNull(portfolio);
    }

    @Test
    public void testRemoveUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");

        assertTrue(portfolioManager.RemoveUser("pedro"));

        Portfolio portfolio = portfolioManager.GetUserPortfolio("pedro");
        assertNull(portfolio);
    }

    @Test
    public void testRemoveNonExistingUserUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        assertFalse(portfolioManager.RemoveUser("non_user"));
    }


    @Test
    public void testGetTotalValue() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");
        Portfolio portfolio = portfolioManager.GetUserPortfolio("pedro");

        portfolio.AddAsset("AAPL", 10.0f, 100.0f);
        portfolio.AddAsset("GOOGL", 5.0f, 3000.0f);

        Float totalValue = portfolioManager.GetTotalValue("pedro");
        assertNotNull(totalValue);
        assertEquals(15255.0f, totalValue);
    }

    @Test
    public void testGetTotalValueNonExistingUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Float totalValue = portfolioManager.GetTotalValue("non_user");
        assertNull(totalValue);
    }

    @Test
    public void testGetTotalValueWithNoAssets() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("empty_user");
        Float totalValue = portfolioManager.GetTotalValue("poor_pedro");
        assertNull(totalValue);
    }
    @Test
    public void testGetPieChart() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("pedro");
        Portfolio portfolio = portfolioManager.GetUserPortfolio("pedro");
        portfolio.AddAsset("AAPL", 10.0f, 150.25f);
        portfolio.AddAsset("GOOGL", 5.0f, 2750.50f);

        Map<String, Float> chartData = portfolioManager.GetPieChart("pedro");
        assertNotNull(chartData);

        float totalPercentage = 0.0f;
        for (float pct : chartData.values()) {
            totalPercentage += pct;
        }

        assertEquals(100.0f, totalPercentage, 0.5f);
        assertTrue(chartData.containsKey("AAPL"));
        assertTrue(chartData.containsKey("GOOGL"));
    }

    @Test
    public void testGetPieChartNonExistingUser() {
        PortfolioManager portfolioManager = new PortfolioManager();
        Map<String, Float> chartData = portfolioManager.GetPieChart("non_user");
        assertNull(chartData);
    }

    @Test
    public void testGetPieChart_NoAssets() {
        PortfolioManager portfolioManager = new PortfolioManager();
        portfolioManager.AddUser("poor_pedro");

        Map<String, Float> chartData = portfolioManager.GetPieChart("poor_pedro");
        assertNull(chartData);
    }
}