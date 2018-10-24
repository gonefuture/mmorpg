package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TThings implements Serializable {
    private Integer thingsId;

    private Long playerId;

    private Integer number;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public Integer getThingsId() {
        return thingsId;
    }

    public void setThingsId(Integer thingsId) {
        this.thingsId = thingsId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", thingsId=").append(thingsId);
        sb.append(", playerId=").append(playerId);
        sb.append(", number=").append(number);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}