package com.smupdates.model;

import java.util.Date;

public class GloblaIndexDetails {

    //Close Last Trade : 13:34 US Time : Sat Feb 24 2018 13:34 //
    //DOW 30 FUTURES 25,314.00 +301.00 +1.20% 25,325.00 24,996.00
    //Symbol Last Chg Chg% High Low
    private String symbol;
    private double ltp;
    private double chg;
    private double perChg;
    private double high;
    private double low;
    private String status;
    private Date Time;

    public GloblaIndexDetails() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLtp() {
        return ltp;
    }

    public void setLtp(double ltp) {
        this.ltp = ltp;
    }

    public double getChg() {
        return chg;
    }

    public void setChg(double chg) {
        this.chg = chg;
    }

    public double getPerChg() {
        return perChg;
    }

    public void setPerChg(double perChg) {
        this.perChg = perChg;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "GloblaIndexDetails{" +
                "symbol='" + symbol + '\'' +
                ", ltp=" + ltp +
                ", chg=" + chg +
                ", perChg=" + perChg +
                ", high=" + high +
                ", low=" + low +
                ", status='" + status + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }
}
