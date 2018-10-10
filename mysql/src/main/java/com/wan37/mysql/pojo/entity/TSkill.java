package com.wan37.mysql.pojo.entity;

import java.io.Serializable;

public class TSkill implements Serializable {
    private Integer id;

    private String name;

    private Integer skillsType;

    private Long cd;

    private Long mpConsumption;

    private Long hpLose;

    private Integer level;

    private Integer state;

    private Long activetime;

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

    public Integer getSkillsType() {
        return skillsType;
    }

    public void setSkillsType(Integer skillsType) {
        this.skillsType = skillsType;
    }

    public Long getCd() {
        return cd;
    }

    public void setCd(Long cd) {
        this.cd = cd;
    }

    public Long getMpConsumption() {
        return mpConsumption;
    }

    public void setMpConsumption(Long mpConsumption) {
        this.mpConsumption = mpConsumption;
    }

    public Long getHpLose() {
        return hpLose;
    }

    public void setHpLose(Long hpLose) {
        this.hpLose = hpLose;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getActivetime() {
        return activetime;
    }

    public void setActivetime(Long activetime) {
        this.activetime = activetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", skillsType=").append(skillsType);
        sb.append(", cd=").append(cd);
        sb.append(", mpConsumption=").append(mpConsumption);
        sb.append(", hpLose=").append(hpLose);
        sb.append(", level=").append(level);
        sb.append(", state=").append(state);
        sb.append(", activetime=").append(activetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}