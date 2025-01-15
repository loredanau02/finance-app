package main.portfolio;

import java.util.HashMap;

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

    public void SetAmount(Float amount) {
        this.amount = amount;
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
}

