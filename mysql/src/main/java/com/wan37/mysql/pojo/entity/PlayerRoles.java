package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class PlayerRoles implements Serializable {
    private Long playerId;

    private Long gameRoleId;

    private static final long serialVersionUID = 1L;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getGameRoleId() {
        return gameRoleId;
    }

    public void setGameRoleId(Long gameRoleId) {
        this.gameRoleId = gameRoleId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", playerId=").append(playerId);
        sb.append(", gameRoleId=").append(gameRoleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}