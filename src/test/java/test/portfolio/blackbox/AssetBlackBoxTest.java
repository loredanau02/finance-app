package test.portfolio.blackbox;

import main.portfolio.Asset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssetBlackBoxTest {
    Float amount = 100.0f;
    Float price = 20.0f;

    @Test
    public void testAssetConstructor() {
        Asset asset = new Asset(amount, price);
        assertEquals(amount, asset.GetAmount());
        assertEquals(price, asset.GetAcquisitionPrice());
        assertFalse(asset.IsFavourite());
    }

    @Test
    public void testBuy() {
        Asset asset = new Asset(amount, price);

        boolean success = asset.AddOrder(50.0f, "BUY", 20.0f);
        assertTrue(success);
        assertEquals(150.0f, asset.GetAmount());
        assertEquals(20.0f, asset.GetAcquisitionPrice());
    }

    @Test
    public void testSell() {
        Asset asset = new Asset(amount, price);

        boolean success = asset.AddOrder(30.0f, "SELL", 0f);

        assertTrue(success);
        assertEquals(70.0f, asset.GetAmount());
        assertEquals(20.0f, asset.GetAcquisitionPrice());
    }

    @Test
    public void testSellMoreThanPresent() {
        Asset asset = new Asset(amount, price);

        boolean success = asset.AddOrder(200.0f, "SELL", 0f);

        assertFalse(success);
        assertEquals(100.0f, asset.GetAmount());
        assertEquals(20.0f, asset.GetAcquisitionPrice());
    }

    @Test
    public void testAcquisitionPriceRecalculation() {
        Asset asset = new Asset(amount, price);

        boolean success = asset.AddOrder(100.0f, "BUY", 30.0f);

        assertTrue(success);
        assertEquals(200.0f, asset.GetAmount());
        assertEquals(25.0f, asset.GetAcquisitionPrice());
    }

    @Test
    public void testSetUnsetFavourite() {
        Asset asset = new Asset(amount, price);
        assertFalse(asset.IsFavourite());

        asset.SetFavourite();
        assertTrue(asset.IsFavourite());

        asset.UnsetFavourite();
        assertFalse(asset.IsFavourite());
    }

    @Test
    public void testCostValueAndPnl() {
        Asset asset = new Asset(amount, price);

        assertEquals(2000.0f, asset.GetInitialCost());
        assertEquals(3000.0f, asset.GetValue(30.0f));
        assertEquals(1000.0f, asset.GetPnl(30.0f));
    }
}
