package main.portfolio;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<String, Asset> assets; // map[asset]amount

    public Portfolio() {
        this.assets = new HashMap<>();
    }

    public boolean AddAsset(String assetName, Float amount, Float acquisitionPrice) {
        if (assets.containsKey(assetName)) {
            return false;
        }
        Asset asset = new Asset(amount, acquisitionPrice);
        assets.put(assetName, asset);
        return true;
    }

    public boolean UpdateAsset(String assetName, Float amount, String side, Float price) {
        if (assets.containsKey(assetName)) {
            Asset asset = assets.get(assetName);
            return asset.AddOrder(amount, side, price);
        }
        return false;
    }

    public Asset GetAsset(String assetName) {
        return assets.get(assetName);
    }

    public Map<String, Asset> GetAssets() {
        return new HashMap<>(assets);
    }

    public boolean RemoveAsset(String assetName) {
        if (!assets.containsKey(assetName)) {
            return false;
        }
        assets.remove(assetName);
        return true;
    }
}
