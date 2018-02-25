package com.smupdates.model;

public class IndexData {
    private String indexName;
    private String open;
    private String high;
    private String low;
    private String ltp;
    private String ch;//change
    private String per;//percentage
    private String yCls;// year change percentage
    private String mCls;//month change percentage
    private String yHigh;
    private String yLow;

    public IndexData() {
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLtp() {
        return ltp;
    }

    public void setLtp(String ltp) {
        this.ltp = ltp;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getyCls() {
        return yCls;
    }

    public void setyCls(String yCls) {
        this.yCls = yCls;
    }

    public String getmCls() {
        return mCls;
    }

    public void setmCls(String mCls) {
        this.mCls = mCls;
    }

    public String getyHigh() {
        return yHigh;
    }

    public void setyHigh(String yHigh) {
        this.yHigh = yHigh;
    }

    public String getyLow() {
        return yLow;
    }

    public void setyLow(String yLow) {
        this.yLow = yLow;
    }

    @Override
    public String toString() {
        return "IndexData{" +
                "indexName='" + indexName + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", ltp='" + ltp + '\'' +
                ", ch='" + ch + '\'' +
                ", per='" + per + '\'' +
                ", yCls='" + yCls + '\'' +
                ", mCls='" + mCls + '\'' +
                ", yHigh='" + yHigh + '\'' +
                ", yLow='" + yLow + '\'' +
                '}';
    }
}