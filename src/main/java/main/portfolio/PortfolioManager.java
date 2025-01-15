package main.portfolio;

import main.profilemanagment.Profile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PortfolioManager {
    private Map<String, Portfolio> portfolios; // Map[username]portfolio
    private Map<String, Float> prices; // map[asset_name]price

    public PortfolioManager() {
        this.portfolios = new HashMap<>();
        this.prices = new HashMap<>();
        System.out.println("Loading prices...");
        loadFromCSV();
        System.out.println("Finished loading prices.");
    }

    private void loadFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("prices.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    try {
                        String asset = data[0].trim();
                        Float price = Float.parseFloat(data[1].trim());
                        prices.put(asset, price);
                        System.out.println(asset + ", " + price);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price format for asset: " + data[0]);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading prices from the CSV file: " + e.getMessage());
        }
    }

    private void initializeDefaultPrices() {
        // Price adapter should fetch prices from a third-party provider.
        // To test the system, and keep it consistent, we populate default prices.
        prices.put("AAPL", 150.25f);   // Apple Inc.
        prices.put("GOOGL", 2750.50f); // Alphabet Inc.
        prices.put("AMZN", 3400.75f);  // Amazon.com Inc.
        prices.put("MSFT", 299.10f);   // Microsoft Corporation
        prices.put("TSLA", 725.60f);   // Tesla Inc.
        prices.put("NFLX", 590.40f);   // Netflix Inc.
        prices.put("NVDA", 220.15f);   // NVIDIA Corporation
    }

    public Float GetPrice(String assetName) {
        return prices.get(assetName);
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

    public boolean AddAsset(String username, String assetName, Float amount, Float aquisitionPrice) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return false;
        }
        return portfolio.AddAsset(assetName, amount, aquisitionPrice);
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

