package com.wan37.mysql.pojo.entity;

import java.io.Serializable;
import java.util.Date;

public class TMissionProgress extends TMissionProgressKey implements Serializable {
    private Integer missionState;

    private Date begintime;

    private Date endtime;

    private String progress;

    private static final long serialVersionUID = 1L;

    public Integer getMissionState() {
        return missionState;
    }

    public void setMissionState(Integer missionState) {
        this.missionState = missionState;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
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
        sb.append(", begintime=").append(begintime);
        sb.append(", endtime=").append(endtime);
        sb.append(", progress=").append(progress);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}