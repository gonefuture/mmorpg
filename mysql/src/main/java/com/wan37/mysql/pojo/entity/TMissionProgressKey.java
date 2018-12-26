package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TMissionProgressKey implements Serializable {
    private Long playerid;

    private Integer missionid;

    private static final long serialVersionUID = 1L;

    public Long getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Long playerid) {
        this.playerid = playerid;
    }

    public Integer getMissionid() {
        return missionid;
    }

    public void setMissionid(Integer missionid) {
        this.missionid = missionid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", playerid=").append(playerid);
        sb.append(", missionid=").append(missionid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}