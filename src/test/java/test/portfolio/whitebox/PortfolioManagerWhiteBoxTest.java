package test.portfolio.whitebox;

import main.portfolio.Portfolio;
import main.portfolio.PortfolioManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortfolioManagerWhiteBoxTest {
    @Test
    public void testConstructor() {
        PortfolioManager manager = new PortfolioManager();
        assertNotNull(manager);
    }

    @Test
    public void testGetNonExistingUserPortfolio() {
        PortfolioManager manager = new PortfolioManager();
        assertNull(manager.GetUserPortfolio("non_user"));
    }

    @Test
    public void testGetEmptyPortfolioTotalValue() {
        PortfolioManager manager = new PortfolioManager();
        manager.AddUser("pedro");
        Float totalValue = manager.GetTotalValue("pedro");
        assertNull(totalValue);
    }

    @Test
    public void testGetTotalValueCustomAsset() {
        PortfolioManager manager = new PortfolioManager();
        manager.AddUser("pedro");
        Portfolio portfolio = manager.GetUserPortfolio("pedro");
        portfolio.AddAsset("HOUSE", 10.0f, 50.0f);

        Float totalValue = manager.GetTotalValue("pedro"); // Should use acquisition price
        assertEquals(500.0f, totalValue);
    }

    @Test
    public void testRemoveNonExistingUserUser() {
        PortfolioManager manager = new PortfolioManager();
        assertFalse(manager.RemoveUser("non_user"));
    }

    @Test
    public void testGetPieChartCustomAssets() {
        PortfolioManager manager = new PortfolioManager();
        manager.AddUser("pedro");
        Portfolio portfolio = manager.GetUserPortfolio("pedro");

        portfolio.AddAsset("AAPL", 5.0f, 140.0f);
        portfolio.AddAsset("HOUSE", 1.0f, 10000000.0f);

        var chartData = manager.GetPieChart("pedro");
        assertNotNull(chartData);

        float sumPercent = 0f;
        for (float percent : chartData.values()) {
            sumPercent += percent;
        }
        assertEquals(100.0f, sumPercent, 1.0f);
    }

    @Test
    public void testGetNonExistingUserPieChart() {
        PortfolioManager manager = new PortfolioManager();
        assertNull(manager.GetPieChart("non_user"));
    }
}
