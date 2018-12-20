package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TGuild implements Serializable {
    private Integer id;

    private String name;

    private Integer level;

    private String member;

    private String warehouse;

    private Integer warehouseSize;

    private String joinRequest;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse == null ? null : warehouse.trim();
    }

    public Integer getWarehouseSize() {
        return warehouseSize;
    }

    public void setWarehouseSize(Integer warehouseSize) {
        this.warehouseSize = warehouseSize;
    }

    public String getJoinRequest() {
        return joinRequest;
    }

    public void setJoinRequest(String joinRequest) {
        this.joinRequest = joinRequest == null ? null : joinRequest.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", level=").append(level);
        sb.append(", member=").append(member);
        sb.append(", warehouse=").append(warehouse);
        sb.append(", warehouseSize=").append(warehouseSize);
        sb.append(", joinRequest=").append(joinRequest);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}