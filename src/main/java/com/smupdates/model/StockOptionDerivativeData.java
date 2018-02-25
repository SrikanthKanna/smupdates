package com.smupdates.model;

import java.util.Date;
import java.util.List;

public class StockOptionDerivativeData {
    private String stockName;
    private Double stockPrice;
    private Date timeStamp;
    private List<OptionData> optionDataList;
    private String expiry;

    public StockOptionDerivativeData(String stockName, Double stockPrice, Date timeStamp) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.timeStamp = timeStamp;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<OptionData> getOptionDataList() {
        return optionDataList;
    }

    public void setOptionDataList(List<OptionData> optionDataList) {
        this.optionDataList = optionDataList;
    }

    public String  getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "StockOptionDerivativeData{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                ", timeStamp='" + timeStamp + '\'' +
                ", optionDataList=" + optionDataList +
                ", expiry=" + expiry +
                '}';
    }
}
