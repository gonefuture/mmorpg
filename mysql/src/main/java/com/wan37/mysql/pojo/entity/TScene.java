package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TScene implements Serializable {
    private Integer id;

    private String name;

    private Integer state;

    private String neighbors;

    private String gameObjectIds;

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(String neighbors) {
        this.neighbors = neighbors == null ? null : neighbors.trim();
    }

    public String getGameObjectIds() {
        return gameObjectIds;
    }

    public void setGameObjectIds(String gameObjectIds) {
        this.gameObjectIds = gameObjectIds == null ? null : gameObjectIds.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", state=").append(state);
        sb.append(", neighbors=").append(neighbors);
        sb.append(", gameObjectIds=").append(gameObjectIds);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}