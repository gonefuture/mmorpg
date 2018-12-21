package com.wan37.gameServer.game.gameRole.model;

import com.wan37.gameServer.game.skills.manager.SkillsManager;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/21 12:30
 * @version 1.00
 * Description: 角色职业
 */

@Data

public class RoleClass {

    @EntityName(column = "id")
    private Integer id;

    @EntityName(column = "职业名称")
    private String name;

    @EntityName(column = "物理攻击")
    private Integer ad;

    @EntityName(column = "法术攻击")
    private Integer ap;

    @EntityName(column = "防御力")
    private Integer def;

    @EntityName(column = "技能")
    private String skills = "";



    private Map<Integer, Skill> skillMap = new HashMap<>();


    public Map<Integer, Skill> getSkillMap() {
        // 如果技能映射不存在，则现在加载
        if (skillMap.size() == 0 && !this.skills.equals("")) {
            String skillsString = this.getSkills();
            Arrays.stream(skillsString.split("\\s+,\\s+"))
                    .map(Integer::valueOf)
                    .map(SkillsManager::get)
                    .forEach(skill -> this.skillMap.put(skill.getKey(),skill));
        }
        return skillMap;
    }

    public void setSkillMap(Map<Integer, Skill> skillMap) {
        this.skillMap = skillMap;
    }
}
