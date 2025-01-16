package main.portfolio;

public class Asset {
    private Float amount;
    private boolean isFavourite;
    private Float acquisitionPrice;

    public Asset(Float amount, Float acquisitionPrice) {
        this.amount = amount;
        this.acquisitionPrice = acquisitionPrice;
        this.isFavourite = false;
    }

    public Float GetAmount() {
        return amount;
    }

    public boolean AddOrder(Float amount, String side, Float price) {
        if (side == null || amount == null || price == null) {
            return false;
        }

        if (amount <= 0 || price < 0) {
            return false;
        }

        if (side.equalsIgnoreCase("BUY")) {
            float totalCost = this.amount * this.acquisitionPrice + amount * price;;

            float newAmount = this.amount + amount;
            if (newAmount <= 0) {
                return false;
            }

            this.acquisitionPrice = totalCost / newAmount;
            this.amount = newAmount;
            return true;
        }

        else if (side.equalsIgnoreCase("SELL")) {
            float newAmount = this.amount - amount;
            if (newAmount < 0) {
                return false;
            }
            this.amount = newAmount;
            return true;
        }
        return false;
    }

    public boolean IsFavourite() {
        return this.isFavourite;
    }

    public void SetFavourite() {
        this.isFavourite = true;
    }

    public void UnsetFavourite() {
        this.isFavourite = false;
    }

    public Float GetAcquisitionPrice() {
        return acquisitionPrice;
    }

    public Float GetInitialCost() {
        return this.amount * this.acquisitionPrice;
    }

    public Float GetValue(Float currentPrice) {
        return this.amount * currentPrice;
    }

    public Float GetPnl(Float currentPrice) {
        return this.GetValue(currentPrice) - this.GetInitialCost();
    }
}
