package com.smupdates.model;

public class OptionData {
    private double strikePrice;
    private OptionDetails call;
    private OptionDetails put;

    public OptionData() {
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public OptionDetails getCall() {
        return call;
    }

    public void setCall(OptionDetails call) {
        this.call = call;
    }

    public OptionDetails getPut() {
        return put;
    }

    public void setPut(OptionDetails put) {
        this.put = put;
    }

    @Override
    public String toString() {
        return "OptionData{" +
                "strikePrice=" + strikePrice +
                ", call=" + call +
                ", put=" + put +
                '}';
    }
}
