package com.smupdates.model;

import java.util.List;

public class LiveEquityFeed {
    private String trdVolumesumMil;
    private String time;
    private List<IndexData> latestData;
    private String trdValueSum;
    private List<EquityData> data;
    private String trdValueSumMil;
    private String trdVolumesum;
    private int advances;
    private int declines;
    private int unchanged;

    public LiveEquityFeed() {
    }

    public String getTrdVolumesumMil() {
        return trdVolumesumMil;
    }

    public void setTrdVolumesumMil(String trdVolumesumMil) {
        this.trdVolumesumMil = trdVolumesumMil;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<IndexData> getLatestData() {
        return latestData;
    }

    public void setLatestData(List<IndexData> latestData) {
        this.latestData = latestData;
    }

    public String getTrdValueSum() {
        return trdValueSum;
    }

    public void setTrdValueSum(String trdValueSum) {
        this.trdValueSum = trdValueSum;
    }

    public List<EquityData> getData() {
        return data;
    }

    public void setData(List<EquityData> data) {
        this.data = data;
    }

    public String getTrdValueSumMil() {
        return trdValueSumMil;
    }

    public void setTrdValueSumMil(String trdValueSumMil) {
        this.trdValueSumMil = trdValueSumMil;
    }

    public String getTrdVolumesum() {
        return trdVolumesum;
    }

    public void setTrdVolumesum(String trdVolumesum) {
        this.trdVolumesum = trdVolumesum;
    }

    public int getAdvances() {
        return advances;
    }

    public void setAdvances(int advances) {
        this.advances = advances;
    }

    public int getDeclines() {
        return declines;
    }

    public void setDeclines(int declines) {
        this.declines = declines;
    }

    public int getUnchanged() {
        return unchanged;
    }

    public void setUnchanged(int unchanged) {
        this.unchanged = unchanged;
    }

    @Override
    public String toString() {
        return "LiveEquityFeed{" +
                "trdVolumesumMil='" + trdVolumesumMil + '\'' +
                ", time='" + time + '\'' +
                ", latestData=" + latestData +
                ", trdValueSum='" + trdValueSum + '\'' +
                ", data=" + data +
                ", trdValueSumMil='" + trdValueSumMil + '\'' +
                ", trdVolumesum='" + trdVolumesum + '\'' +
                ", advances=" + advances +
                ", declines=" + declines +
                ", unchanged=" + unchanged +
                '}';
    }
}
