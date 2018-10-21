package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TGoods implements Serializable {
    private Long id;

    private String name;

    private Integer number;

    private Integer buffer;

    private Boolean isBingding;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getBuffer() {
        return buffer;
    }

    public void setBuffer(Integer buffer) {
        this.buffer = buffer;
    }

    public Boolean getIsBingding() {
        return isBingding;
    }

    public void setIsBingding(Boolean isBingding) {
        this.isBingding = isBingding;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", number=").append(number);
        sb.append(", buffer=").append(buffer);
        sb.append(", isBingding=").append(isBingding);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}