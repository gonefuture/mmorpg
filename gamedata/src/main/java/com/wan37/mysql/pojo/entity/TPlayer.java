package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TPlayer implements Serializable {
    private Long id;

    private String name;

    private Integer exp;

    private Integer state;

    private Integer scene;

    private Long userId;

    private String equipments;

    private Integer money;

    private Integer guildId;

    private Integer roleClass;

    private Integer guildClass;

    private String friends;

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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
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

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments == null ? null : equipments.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getGuildId() {
        return guildId;
    }

    public void setGuildId(Integer guildId) {
        this.guildId = guildId;
    }

    public Integer getRoleClass() {
        return roleClass;
    }

    public void setRoleClass(Integer roleClass) {
        this.roleClass = roleClass;
    }

    public Integer getGuildClass() {
        return guildClass;
    }

    public void setGuildClass(Integer guildClass) {
        this.guildClass = guildClass;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends == null ? null : friends.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", exp=").append(exp);
        sb.append(", state=").append(state);
        sb.append(", scene=").append(scene);
        sb.append(", userId=").append(userId);
        sb.append(", equipments=").append(equipments);
        sb.append(", money=").append(money);
        sb.append(", guildId=").append(guildId);
        sb.append(", roleClass=").append(roleClass);
        sb.append(", guildClass=").append(guildClass);
        sb.append(", friends=").append(friends);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}