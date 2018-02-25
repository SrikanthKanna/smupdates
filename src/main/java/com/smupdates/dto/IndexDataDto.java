package com.smupdates.dto;

public class IndexDataDto {

    private String indexName;
    private double open;
    private double high;
    private double low;
    private double ltp;
    private double ch;//change
    private double per;//percentage
    private double yCls;// year change percentage
    private double mCls;//month change percentage
    private double yHigh;
    private double yLow;

    public IndexDataDto() {
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
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

    public double getLtp() {
        return ltp;
    }

    public void setLtp(double ltp) {
        this.ltp = ltp;
    }

    public double getCh() {
        return ch;
    }

    public void setCh(double ch) {
        this.ch = ch;
    }

    public double getPer() {
        return per;
    }

    public void setPer(double per) {
        this.per = per;
    }

    public double getyCls() {
        return yCls;
    }

    public void setyCls(double yCls) {
        this.yCls = yCls;
    }

    public double getmCls() {
        return mCls;
    }

    public void setmCls(double mCls) {
        this.mCls = mCls;
    }

    public double getyHigh() {
        return yHigh;
    }

    public void setyHigh(double yHigh) {
        this.yHigh = yHigh;
    }

    public double getyLow() {
        return yLow;
    }

    public void setyLow(double yLow) {
        this.yLow = yLow;
    }

    @Override
    public String toString() {
        return "IndexDataDto{" +
                "indexName='" + indexName + '\'' +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", ltp=" + ltp +
                ", ch=" + ch +
                ", per=" + per +
                ", yCls=" + yCls +
                ", mCls=" + mCls +
                ", yHigh=" + yHigh +
                ", yLow=" + yLow +
                '}';
    }
}
