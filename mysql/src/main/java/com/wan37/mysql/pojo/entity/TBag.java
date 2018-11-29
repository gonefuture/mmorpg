package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TBag extends TBagKey implements Serializable {
    private Integer bagSize;

    private String bagName;

    private String goods;

    private static final long serialVersionUID = 1L;

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
        sb.append(", bagSize=").append(bagSize);
        sb.append(", bagName=").append(bagName);
        sb.append(", goods=").append(goods);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}