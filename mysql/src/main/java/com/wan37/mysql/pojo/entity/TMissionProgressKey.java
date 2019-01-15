package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TMissionProgressKey implements Serializable {
    private Long playerId;

    private Integer missionId;

    private static final long serialVersionUID = 1L;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", playerId=").append(playerId);
        sb.append(", missionId=").append(missionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}