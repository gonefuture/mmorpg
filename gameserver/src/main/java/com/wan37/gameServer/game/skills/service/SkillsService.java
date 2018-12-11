package com.wan37.gameServer.game.skills.service;

import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.service.BufferService;
import com.wan37.gameServer.game.skills.manager.SkillsCacheMgr;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.gameServer.model.Creature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 12:05
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class SkillsService {

    @Resource
    private SkillsCacheMgr skillsCacheMgr;


    @Resource
    private BufferService bufferService;


    @Resource
    private TaskManager taskManager;

    @Resource
    private NotificationManager notificationManager;

    /**
     *
     * @param creature  活物
     * @param skillId  技能id
     * @return true 可是使用。 false不可以使用
     */
    public boolean checkCD(Creature creature, Integer skillId) {
        Skill playerSkill = creature.getSkillMap().get(skillId);
        // 如果没有使用这个技能，表示可以使用
        return playerSkill == null;
    }


    /**
     *  开始技能
     * @param creature 活物
     * @param skill 技能
     */
    public void startSkill(Creature creature, Skill skill) {
        Skill playerSkill = new Skill();
        playerSkill.setName(skill.getName());
        playerSkill.setCd(skill.getCd());
        playerSkill.setMpConsumption(skill.getMpConsumption());
        playerSkill.setLevel(skill.getLevel());
        playerSkill.setActiveTime(System.currentTimeMillis());
        creature.getSkillMap().put(skill.getId(),playerSkill);
        // 技能cd结束后，移出活物cd状态
        playerSkill.setActiveTime(System.currentTimeMillis());
        taskManager.schedule(skill.getCd(), () -> {
            creature.getSkillMap().remove(skill.getKey());
            return null;
        });
    }

    /**
     *  通过技能id获取技能
     * @param skillId 技能id
     * @return 技能
     */
    public Skill getSkill(Integer skillId) {
        return skillsCacheMgr.get(skillId);
    }


    /**
     *   活物使用技能
     * @param initiator 技能发起者
     * @param target 技能目标
     * @param skill 技能
     * @return 是否成功
     */
    public boolean useSkill(Creature initiator, Creature target ,Skill skill) {

        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHpLose());

        // 如果技能类型是2，则对敌方单体目标释放buffer
        if (skill.getSkillsType() == 2) {
            Buffer buffer = bufferService.getTBuffer(skill.getBuffer());
            // 如果buffer存在则启动buffer
            Optional.ofNullable(buffer).map(
                    (b) -> bufferService.startBuffer(target,b)
            );
        }
        // 开启技能冷却
        startSkill(initiator,skill);
        return true;
    }

    /**
     *  释放群体技能
     * @param initiator 施放者
     * @param targetList 目标群体
     * @param skill 技能
     * @return  是否使用成功
     */
    public boolean groupSkill(Creature initiator, List<Creature> targetList , Skill skill) {

        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        targetList.forEach(
                target ->  {
                    target.setHp(target.getHp() - skill.getHpLose());
                    if(skill.getBuffer() != 0) {
                        Buffer buffer = bufferService.getTBuffer(skill.getBuffer());
                        // 如果buffer存在则启动buffer
                        Optional.ofNullable(buffer).map(
                                (b) -> bufferService.startBuffer(target,b)
                        );
                    }
                }
        );
        // 开启技能冷却
        startSkill(initiator,skill);
        return true;
    }







}
