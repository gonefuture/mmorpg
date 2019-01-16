package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.player.manager.RoleClassManager;
import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.model.RoleClass;
import com.wan37.gameserver.game.player.service.BufferService;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.game.skills.manager.SkillsManager;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.game.skills.model.SkillType;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.manager.task.TimedTaskManager;
import com.wan37.gameserver.model.Creature;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 12:05
 * @version 1.00
 * Description: mmorpg
 */
@Service
@Slf4j
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

    @Resource
    SkillEffect skillEffect;

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
    public void startSkillCd(Creature creature, Skill skill) {
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
    public boolean castSkill(Creature initiator, Creature target , GameScene gameScene, Skill skill) {
        long start = System.currentTimeMillis();
        // 如果技能施法时间不少于0
        if (!skill.getCastTime().equals(0)) {
            notificationManager.notifyCreature(initiator,
                    MessageFormat.format("开始施法，吟唱需要{0}秒",skill.getCastTime()));
            // 按吟唱时间延迟执行
            TimedTaskManager.singleThreadSchedule( skill.getCastTime()*1000,
                    () -> {
                            // 开启技能冷却
                            startSkillCd(initiator, skill);
                            gameScene.getSingleThreadSchedule().execute(
                                    () -> {
                                        // 注意，这里的技能进行还是要用场景执行器执行，不然会导致多线程问题
                                        log.debug("延迟时间{}", System.currentTimeMillis() - start);
                                        skillEffect.castSkill(skill.getSkillType(), initiator, target, gameScene, skill);
                                    }
                            );
                        }
            );
        } else {
            skillEffect.castSkill(skill.getSkillType(),initiator,target,gameScene,skill);
            // 开启技能冷却
            startSkillCd(initiator,skill);
        }
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
                    target.setHp(target.getHp() - skill.getHurt());
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
        startSkillCd(initiator,skill);
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
        if (creature.getMp() < skill.getMpConsumption()) {
            if (creature instanceof Player) {
                notificationManager.notifyPlayer((Player) creature,"您的mp不足");
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

    /**
     *  自身使用技能
     */
    public void useSkillSelf(ChannelHandlerContext cxt, Integer skillId) {
        Skill skill = SkillsManager.get(skillId);
        Player player = playerDataService.getPlayerByCtx(cxt);
        GameScene gameScene = gameSceneService.getSceneByPlayer(player);

        if (!canSkill(player,skill)) {
            notificationManager.notifyCreature(player,"你现在不能使用这个技能");
            return;
        }
        // 技能类型为4则对友方使用
        if (skill.getSkillType().equals(SkillType.FRIENDLY.getTypeId())) {
            castSkill(player,player,gameScene,skill);
        } else {
            notificationManager.notifyCreature(player,"这个技能不能对自身或友方使用");
        }
    }
}
