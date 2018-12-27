package com.wan37.mysql.pojo.entity;

import java.io.Serializable;
import java.util.Date;

public class TMissionProgress extends TMissionProgressKey implements Serializable {
    private Integer missionState;

    private Date beginTime;

    private Date endTime;

    private String progress;

    private static final long serialVersionUID = 1L;

    public Integer getMissionState() {
        return missionState;
    }

    public void setMissionState(Integer missionState) {
        this.missionState = missionState;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress == null ? null : progress.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", missionState=").append(missionState);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", progress=").append(progress);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}