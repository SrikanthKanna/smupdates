package com.smupdates.model;

public class OptionDetails {
    private double oi;
    private double chnginOI;
    private double volume;
    private double impliedVoltality;
    private double ltp;
    private double netChng;
    private double bidQty;
    private double bidPrice;
    private double askPrice;
    private double askQty;

    public OptionDetails() {
    }

    public double getOi() {
        return oi;
    }

    public void setOi(double oi) {
        this.oi = oi;
    }

    public double getChnginOI() {
        return chnginOI;
    }

    public void setChnginOI(double chnginOI) {
        this.chnginOI = chnginOI;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getImpliedVoltality() {
        return impliedVoltality;
    }

    public void setImpliedVoltality(double impliedVoltality) {
        this.impliedVoltality = impliedVoltality;
    }

    public double getLtp() {
        return ltp;
    }

    public void setLtp(double ltp) {
        this.ltp = ltp;
    }

    public double getNetChng() {
        return netChng;
    }

    public void setNetChng(double netChng) {
        this.netChng = netChng;
    }

    public double getBidQty() {
        return bidQty;
    }

    public void setBidQty(double bidQty) {
        this.bidQty = bidQty;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public double getAskQty() {
        return askQty;
    }

    public void setAskQty(double askQty) {
        this.askQty = askQty;
    }

    @Override
    public String toString() {
        return "OptionDetails{" +
                "oi=" + oi +
                ", chnginOI=" + chnginOI +
                ", volume=" + volume +
                ", impliedVoltality=" + impliedVoltality +
                ", ltp=" + ltp +
                ", netChng=" + netChng +
                ", bidQty=" + bidQty +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                ", askQty=" + askQty +
                '}';
    }
}
