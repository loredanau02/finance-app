package main.portfolio;

import main.profilemanagment.Profile;

import java.util.HashMap;
import java.util.Map;

public class PortfolioManager {
    private Map<String, Portfolio> portfolios; // Map[username]portfolio
    private Map<String, Float> prices; // map[asset_name]price

    public PortfolioManager() {
        this.portfolios = new HashMap<>();
        this.prices = new HashMap<>();
    }

    public boolean AddUser(String username) {
        if (portfolios.containsKey(username)) {
            return false; // User already exists
        }
        portfolios.put(username, new Portfolio());
        return true;
    }

    public boolean RemoveUser(String username) {
        if (!portfolios.containsKey(username)) {
            return false;
        }
        portfolios.remove(username);
        return true;
    }

    public Portfolio GetUserPortfolio(String username) {
        return portfolios.get(username);
    }

    public boolean AddAsset(String username, String assetName, Float amount) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.AddAsset(assetName, amount);
    }

    public boolean UpdateAssetAmount(String username, String assetName, Float amount) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.UpdateAssetAmount(assetName, amount);
    }

    public Asset GetAsset(String username, String assetName) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return null;
        }
        return portfolio.GetAsset(assetName);
    }

    public Map<String, Asset> GetAssets(String username) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return null;
        }
        return portfolio.GetAssets();
    }

    public boolean RemoveAsset(String username, String assetName) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.RemoveAsset(assetName);
    }
}

