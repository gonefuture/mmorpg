package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TPlayer implements Serializable {
    private Long id;

    private String name;

    private Long hp;

    private Long mp;

    private String position;

    private Integer state;

    private Integer scene;

    private Long userId;

    private String bag;

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

    public Long getHp() {
        return hp;
    }

    public void setHp(Long hp) {
        this.hp = hp;
    }

    public Long getMp() {
        return mp;
    }

    public void setMp(Long mp) {
        this.mp = mp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBag() {
        return bag;
    }

    public void setBag(String bag) {
        this.bag = bag == null ? null : bag.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", hp=").append(hp);
        sb.append(", mp=").append(mp);
        sb.append(", position=").append(position);
        sb.append(", state=").append(state);
        sb.append(", scene=").append(scene);
        sb.append(", userId=").append(userId);
        sb.append(", bag=").append(bag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}