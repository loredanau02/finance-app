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

    public Portfolio getUserPortfolio(String username) {
        return  portfolios.get(username);
    }

    public boolean addAsset(String username, String assetName, Float amount) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.AddAsset(assetName, amount);
    }

    public boolean updateAsset(String username, String assetName, Float amount) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.UpdateAsset(assetName, amount);
    }

    public boolean removeAsser(String username, String assetName) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.RemoveAsset(assetName);
    }


}

