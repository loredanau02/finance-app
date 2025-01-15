package main.portfolio;

import java.util.HashMap;

public class Asset {
    private Float amount;

    public Asset(Float amount) {
        this.amount = amount;
    }

    public Float GetAmount() {
        return amount;
    }

    public void SetAmount(Float amount) {
        this.amount = amount;
    }
}

