package test.portfolio.whitebox;

import main.portfolio.Asset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetWhiteBoxTest {
    Float amount = 100.0f;
    Float price = 20.0f;

    @Test
    public void testAddOrderOrderWithEmptyParam() {
        Asset asset = new Asset(amount, price);

        assertFalse(asset.AddOrder(null, "BUY", 10.0f)); // Empty amount
        assertFalse(asset.AddOrder(10.0f, null, 10.0f)); // Empty side
        assertFalse(asset.AddOrder(10.0f, "BUY", null)); // Empty price
    }

    @Test
    public void testAddOrderOrderWithNegativeOrZeroParams() {
        Asset asset = new Asset(amount, price);

        assertFalse(asset.AddOrder(0.0f, "BUY", 10.0f)); // Amount is zero
        assertFalse(asset.AddOrder(-10.0f, "BUY", 10.0f)); // Negative amount
        assertFalse(asset.AddOrder(10.0f, "BUY", -1.0f)); // Negative price
    }


    @Test
    public void testAddOrderOrderSellMoreThanPresent() {
        Asset asset = new Asset(amount, price);

        assertFalse(asset.AddOrder(150.0f, "SELL", 0.0f)); // Sell more than asset amount
        assertEquals(100.0f, asset.GetAmount());
    }

    @Test
    public void testAddOrderOrderInvalidSide() {
        Asset asset = new Asset(amount, price);

        assertFalse(asset.AddOrder(10.0f, "invalid_side", 10.0f));
        assertEquals(100.0f, asset.GetAmount());
        assertEquals(20.0f, asset.GetAcquisitionPrice());
    }

    @Test
    public void testAddOrderOrdersBuyAndSell() {
        Asset asset = new Asset(amount, price);

        assertTrue(asset.AddOrder(10.0f, "BUY", 20.0f));
        assertEquals(110.0f, asset.GetAmount());

        assertTrue(asset.AddOrder(10.0f, "SELL", 0.0f));
        assertEquals(100.0f, asset.GetAmount());
    }
}
