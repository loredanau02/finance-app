package main.portfolio;

import main.profilemanagment.Profile;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private Map<String, Float> assets; // map[asset_name]amount

    public Portfolio() {
        this.assets = new HashMap<>();
    }

    public boolean AddAsset(String assetName, Float amount) {
        if (assets.containsKey(username)) {
            return false;
        }
        assets.put(assetName, amount);
        return true;
    }
    public boolean UpdateAsset(String assetName, Float amount) {
        assets.put(assetName, amount);
        return true;
    }
    public boolean RemoveAsset(String assetName) {
        if (!assets.containsKey(assetName)) {
            return false;
        }
        assets.remove(assetName);
        return true;
    }
}
