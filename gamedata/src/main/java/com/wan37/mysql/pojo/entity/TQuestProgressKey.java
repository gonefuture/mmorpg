package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TQuestProgressKey implements Serializable {
    private Long playerId;

    private Integer questId;

    private static final long serialVersionUID = 1L;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", playerId=").append(playerId);
        sb.append(", questId=").append(questId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}