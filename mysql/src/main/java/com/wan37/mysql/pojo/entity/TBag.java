package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TBag extends TBagKey implements Serializable {
    private Integer bagSize;

    private String bagName;

    private String items;

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

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items == null ? null : items.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bagSize=").append(bagSize);
        sb.append(", bagName=").append(bagName);
        sb.append(", items=").append(items);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}