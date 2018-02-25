package com.smupdates.dto;

import java.util.Date;
import java.util.List;

public class LiveEquityFeedDto {

    private Date time;
    private List<IndexDataDto> latestData;
    private List<EquityDataDto> data;
    private double trdValueSum;
    private double trdValueSumMil;
    private double trdVolumesum;
    private double trdVolumesumMil;
    private int advances;
    private int declines;
    private int unchanged;

    public LiveEquityFeedDto() {
    }

    public double getTrdVolumesumMil() {
        return trdVolumesumMil;
    }

    public void setTrdVolumesumMil(double trdVolumesumMil) {
        this.trdVolumesumMil = trdVolumesumMil;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<IndexDataDto> getLatestData() {
        return latestData;
    }

    public void setLatestData(List<IndexDataDto> latestData) {
        this.latestData = latestData;
    }

    public double getTrdValueSum() {
        return trdValueSum;
    }

    public void setTrdValueSum(double trdValueSum) {
        this.trdValueSum = trdValueSum;
    }

    public List<EquityDataDto> getData() {
        return data;
    }

    public void setData(List<EquityDataDto> data) {
        this.data = data;
    }

    public double getTrdValueSumMil() {
        return trdValueSumMil;
    }

    public void setTrdValueSumMil(double trdValueSumMil) {
        this.trdValueSumMil = trdValueSumMil;
    }

    public double getTrdVolumesum() {
        return trdVolumesum;
    }

    public void setTrdVolumesum(double trdVolumesum) {
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
        return "LiveEquityFeedDto{" +
                "trdVolumesumMil=" + trdVolumesumMil +
                ", time=" + time +
                ", latestData=" + latestData +
                ", trdValueSum=" + trdValueSum +
                ", data=" + data +
                ", trdValueSumMil=" + trdValueSumMil +
                ", trdVolumesum=" + trdVolumesum +
                ", advances=" + advances +
                ", declines=" + declines +
                ", unchanged=" + unchanged +
                '}';
    }
}
