package com.smupdates.dto;

public class EquityDataDto {

    private String symbol;
    private String cAct; //corporate aaction
    private double open;
    private double high;
    private double low;
    private double ltP;
    private double ptsC;
    private double per;
    private double trdVol; //trade volume
    private double ntP; //Turnover(crs.)
    private double wkhi; //52 week high
    private double wklo; //52 week low
    private double previousClose;
    private double dayEndClose;
    private double iislPtsChange;
    private double iislPercChange;
    private double yPC; //yearly percentage change
    private double mPC; //monthly percentage change

    public EquityDataDto() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
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

    public double getLtP() {
        return ltP;
    }

    public void setLtP(double ltP) {
        this.ltP = ltP;
    }

    public double getPtsC() {
        return ptsC;
    }

    public void setPtsC(double ptsC) {
        this.ptsC = ptsC;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    public double getTrdVol() {
        return trdVol;
    }

    public void setTrdVol(double trdVol) {
        this.trdVol = trdVol;
    }

    public double getNtP() {
        return ntP;
    }

    public void setNtP(double ntP) {
        this.ntP = ntP;
    }

    public double getWkhi() {
        return wkhi;
    }

    public void setWkhi(double wkhi) {
        this.wkhi = wkhi;
    }

    public double getWklo() {
        return wklo;
    }

    public void setWklo(double wklo) {
        this.wklo = wklo;
    }

    public String getcAct() {
        return cAct;
    }

    public void setcAct(String cAct) {
        this.cAct = cAct;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getDayEndClose() {
        return dayEndClose;
    }

    public void setDayEndClose(double dayEndClose) {
        this.dayEndClose = dayEndClose;
    }

    public double getIislPtsChange() {
        return iislPtsChange;
    }

    public void setIislPtsChange(double iislPtsChange) {
        this.iislPtsChange = iislPtsChange;
    }

    public double getIislPercChange() {
        return iislPercChange;
    }

    public void setIislPercChange(double iislPercChange) {
        this.iislPercChange = iislPercChange;
    }

    public double getyPC() {
        return yPC;
    }

    public void setyPC(double yPC) {
        this.yPC = yPC;
    }

    public double getmPC() {
        return mPC;
    }

    public void setmPC(double mPC) {
        this.mPC = mPC;
    }

    @Override
    public String toString() {
        return "EquityDataDto{" +
                "symbol='" + symbol + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", ltP=" + ltP +
                ", ptsC=" + ptsC +
                ", per=" + per +
                ", trdVol=" + trdVol +
                ", ntP=" + ntP +
                ", wkhi=" + wkhi +
                ", wklo=" + wklo +
                ", cAct='" + cAct + '\'' +
                ", previousClose=" + previousClose +
                ", dayEndClose=" + dayEndClose +
                ", iislPtsChange=" + iislPtsChange +
                ", iislPercChange=" + iislPercChange +
                ", yPC=" + yPC +
                ", mPC=" + mPC +
                '}';
    }
}
