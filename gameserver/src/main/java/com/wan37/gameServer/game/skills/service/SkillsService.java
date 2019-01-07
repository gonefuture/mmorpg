package com.wan37.gameServer.game.skills.service;

import com.wan37.gameServer.game.player.manager.RoleClassManager;
import com.wan37.gameServer.game.player.model.Buffer;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.model.RoleClass;
import com.wan37.gameServer.game.player.service.BufferService;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.skills.manager.SkillsManager;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TimedTaskManager;
import com.wan37.gameServer.model.Creature;
import io.netty.channel.ChannelHandlerContext;
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
    private BufferService bufferService;

    @Resource
    private PetService petService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService gameSceneService;




    /**
     *
     * @param creature  活物
     * @param skillId  技能id
     * @return true 可是使用。 false不可以使用
     */
    public boolean inCD(Creature creature, Integer skillId) {
        Skill playerSkill = creature.getHasUseSkillMap().get(skillId);
        // 如果没有使用这个技能，表示可以使用
        return playerSkill != null;
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
        creature.getHasUseSkillMap().put(skill.getId(),playerSkill);
        // 技能cd结束后，移出活物cd状态
        playerSkill.setActiveTime(System.currentTimeMillis());
        TimedTaskManager.schedule(skill.getCd(), () -> creature.getHasUseSkillMap().remove(skill.getKey()) );
    }

    /**
     *  通过技能id获取技能
     * @param skillId 技能id
     * @return 技能
     */
    public Skill getSkill(Integer skillId) {
        return SkillsManager.get(skillId);
    }


    /**
     *   活物使用技能
     * @param initiator 技能发起者
     * @param target 技能目标
     * @param skill 技能
     * @return 是否成功
     */
    public boolean useSkill(Creature initiator, Creature target , GameScene gameScene, Skill skill) {

        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHpLose());

        // 如果技能类型是2，则对敌方单体目标释放buffer
        if (skill.getSkillType() == 2) {
            Buffer buffer = bufferService.getTBuffer(skill.getBuffer());
            // 如果buffer存在则启动buffer
            Optional.ofNullable(buffer).map(
                    (b) -> bufferService.startBuffer(target,b)
            );
        }

        // 召唤兽类型的技能
        if (skill.getSkillType() == 5 ) {
            petService.callPet(initiator,target,gameScene,skill.getCall());
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

    /**
     *      检测玩家是否可以使用技能
     * @param creature    玩家
     * @param skill 技能
     * @return 是否可用
     */
    public boolean canSkill(Creature creature, Skill skill) {


        if ( null == skill) {
            if (creature instanceof Player) {
                notificationManager.notifyPlayer((Player) creature,"该技能不存在");
            }
            return false;
        }

        if (creature instanceof Player) {
            Player player = (Player) creature;
            RoleClass roleClass = RoleClassManager.getRoleClass(player.getRoleClass());
            if (null == roleClass.getSkillMap().get(skill.getKey())) {
                notificationManager.notifyPlayer(player,"该职业并没有这个技能");
                return false;
            }

        }

        // 检查技能冷却，
        if (inCD(creature,skill.getId()) ){
            if (creature instanceof Player) {
            notificationManager.notifyPlayer((Player) creature,"你还不能使用该技能，还在冷却中");
            }
            return false;
        }

        return true;

    }


    public void useSkillSelf(ChannelHandlerContext cxt, Integer skillId) {
        Skill skill = SkillsManager.get(skillId);
        Player player = playerDataService.getPlayerByCtx(cxt);
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);

        if (!canSkill(player,skill)) {
            return;
        }
        // 技能类型为1则对自己使用
        if (skill.getSkillType() == 1 ) {
            useSkill(player,player,gameScene,skill);
        }
    }
}
