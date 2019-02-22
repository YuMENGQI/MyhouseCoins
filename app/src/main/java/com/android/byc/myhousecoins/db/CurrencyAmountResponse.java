package com.android.byc.myhousecoins.db;

import com.google.gson.annotations.SerializedName;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 16:47
 * @description
 */
public class CurrencyAmountResponse {

    @SerializedName("CurrencyAmount")
    private int currencyCoinsAmount;

    public int getCurrencyCoinsAmount() {
        return currencyCoinsAmount;
    }

    public void setCurrencyCoinsAmount(int currencyCoinsAmount) {
        this.currencyCoinsAmount = currencyCoinsAmount;
    }
}
