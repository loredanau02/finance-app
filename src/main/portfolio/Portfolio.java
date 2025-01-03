package main.portfolio;

import main.profilemanagment.Profile;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<String, Asset> assets; // map[asset_name]amount

    public Portfolio() {
        this.assets = new HashMap<>();
    }

    public boolean AddAsset(String assetName, Float amount) {
        if (assets.containsKey(assetName)) {
            return false;
        }
        Asset asset = new Asset(amount);
        assets.put(assetName, asset);
        return true;
    }

    public boolean UpdateAssetAmount(String assetName, Float amount) {
        if (assets.containsKey(assetName)) {
            Asset asset = assets.get(assetName);
            asset.SetAmount(amount);
            return true;
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
