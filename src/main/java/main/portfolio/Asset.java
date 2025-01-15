package main.portfolio;

import java.util.HashMap;

public class Asset {
    private Float amount;
    private boolean isFavourite;

    public Asset(Float amount) {
        this.amount = amount;
    }

    public Float GetAmount() {
        return amount;
    }

    public void SetAmount(Float amount) {
        this.amount = amount;
        this.isFavourite = false;
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
}

