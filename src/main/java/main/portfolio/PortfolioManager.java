package main.portfolio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PortfolioManager {
    private Map<String, Portfolio> portfolios; // map[username]portfolio
    private Map<String, Float> prices; // map[asset_name]price

    public PortfolioManager() {
        this.portfolios = new HashMap<>();
        this.prices = new HashMap<>();
        loadFromCSV();
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

    public Float GetPrice(String assetName) {
        return prices.get(assetName);
    }

    public Map<String, Float> GetPrices() {
        return prices;
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

    public Float GetTotalValue(String username) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return null;
        }
        Map<String, Asset> assets = portfolio.GetAssets();
        if (assets == null || assets.isEmpty()) {
            return null;
        }
        float totalValue = 0.0f;
        for (Map.Entry<String, Asset> entry : assets.entrySet()) {
            String assetName = entry.getKey();
            Float price = prices.get(assetName);
            if (price == null) {
                price = entry.getValue().GetAcquisitionPrice();
            }
            totalValue += entry.getValue().GetValue(price);
        }
        return totalValue;
    }

    public Map<String, Float> GetPieChart(String username) {
        Portfolio portfolio = portfolios.get(username);
        if (portfolio == null) {
            return null;
        }

        Map<String, Asset> assets = portfolio.GetAssets();
        if (assets == null || assets.isEmpty()) {
            return null;
        }

        Float totalValue = GetTotalValue(username);
        if (totalValue == null) {
            return null;
        }

        Map<String, Float> chartData = new HashMap<>();
        for (Map.Entry<String, Asset> entry : assets.entrySet()) {
            String assetName = entry.getKey();
            Asset asset = entry.getValue();

            Float price = prices.get(assetName);
            if (price == null) {
                price = asset.GetAcquisitionPrice();
            }

            Float assetValue = asset.GetValue(price);
            float percentage = (assetValue / totalValue) * 100;
            chartData.put(assetName, percentage);
        }

        return chartData;
    }
}

