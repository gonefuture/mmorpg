package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TBag implements Serializable {
    private Long playerId;

    private Integer bagSize;

    private String bagName;

    private Integer type;

    private String goods;

    private static final long serialVersionUID = 1L;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Integer getBagSize() {
        return bagSize;
    }

    public void setBagSize(Integer bagSize) {
        this.bagSize = bagSize;
    }

    public String getBagName() {
        return bagName;
    }

    public void setBagName(String bagName) {
        this.bagName = bagName == null ? null : bagName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods == null ? null : goods.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", playerId=").append(playerId);
        sb.append(", bagSize=").append(bagSize);
        sb.append(", bagName=").append(bagName);
        sb.append(", type=").append(type);
        sb.append(", goods=").append(goods);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}