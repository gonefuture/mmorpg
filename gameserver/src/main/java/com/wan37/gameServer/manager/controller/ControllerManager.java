package com.wan37.gameServer.manager.controller;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.bag.controller.EquipmentController;
import com.wan37.gameServer.game.bag.controller.RemoveEquipController;
import com.wan37.gameServer.game.bag.controller.ShowBagsController;
import com.wan37.gameServer.game.bag.controller.ShowEquipmentController;
import com.wan37.gameServer.game.chat.controller.PublicChatController;
import com.wan37.gameServer.game.chat.controller.WhisperController;
import com.wan37.gameServer.game.combat.controller.CommonAttackController;
import com.wan37.gameServer.game.combat.controller.PVPController;
import com.wan37.gameServer.game.combat.controller.SkillPVPController;
import com.wan37.gameServer.game.gameInstance.controller.EnterInstanceController;
import com.wan37.gameServer.game.gameRole.controller.*;
import com.wan37.gameServer.game.mail.controller.GetMailController;
import com.wan37.gameServer.game.mail.controller.MailListController;
import com.wan37.gameServer.game.mail.controller.SendMailController;
import com.wan37.gameServer.game.scene.controller.AOIController;
import com.wan37.gameServer.game.scene.controller.LocationController;
import com.wan37.gameServer.game.scene.controller.PlayerMoveController;
import com.wan37.gameServer.game.shop.controller.BuyGoodsController;
import com.wan37.gameServer.game.shop.controller.ShowGoodsController;
import com.wan37.gameServer.game.skills.controller.UseSkillsAttackMonsterController;
import com.wan37.gameServer.game.things.controller.UseItemController;
import com.wan37.gameServer.game.user.controller.ListGameRoleController;
import com.wan37.gameServer.game.user.controller.UserLoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 14:57
 * @version 1.00
 * Description: 控制器管理。 通过msgId映射到其关联的映射器处理。
 */

@Component
@Slf4j
public class ControllerManager {

    // MsgId标志和服务之间的映射
    private Map<MsgId,IController>  controllerMapping = new HashMap<>();


    ControllerManager(){

        log.info("控制器管理器初始化成功");
    }

    @Resource
    private UserLoginController userLoginController;

    @Resource
    private PlayerLoginController playerLoginController;

    @Resource
    private PlayerMoveController playerMoveController;

    @Resource
    private ListGameRoleController listGameRoleController;

    @Resource
    private AOIController aoiController;

    @Resource
    private PlayerQuitController playerQuitController;


    @Resource
    private UseSkillsAttackMonsterController useSkillsAttackMonsterController;

    @Resource
    private StartBufferController startBufferController;

    @Resource
    private EquipmentController equipmentController;

    @Resource
    private UseItemController useItemController;

    @Resource
    private ShowBagsController showBagsController;

    @Resource
    private CommonAttackController commonAttackController;

    @Resource
    private LocationController locationController;

    @Resource
    private EnterInstanceController enterInstanceController;

    @Resource
    private ShowEquipmentController showEquipmentController;

    @Resource
    private ShowPlayerController showPlayerController;

    @Resource
    private RemoveEquipController removeEquipController;

    @Resource
    private BuyGoodsController buyGoodsController;

    @Resource
    private ShowGoodsController showGoodsController;

    @Resource
    private WhisperController whisperController;

    @Resource
    private PublicChatController publicChatController;

    @Resource
    private SendMailController sendMailController;

    @Resource
    private MailListController mailListController;

    @Resource
    private GetMailController getMailController;

    @Resource
    private PVPController pvpController;

    @Resource
    private SkillPVPController skillPVPController;


    // 加载MsgId与控制器之间的关系
    @PostConstruct
    private void init() {
        add(MsgId.USER_LOGIN, userLoginController);
        add(MsgId.PLAYER_LOGIN,playerLoginController);
        add(MsgId.MOVE, playerMoveController);
        add(MsgId.LIST_GAME_ROLES,listGameRoleController);
        add(MsgId.AOI,aoiController);
        add(MsgId.PLAYER_EXIT,playerQuitController);
        add(MsgId.USE_SKILLS, useSkillsAttackMonsterController);
        add(MsgId.START_BUFFER,startBufferController);
        add(MsgId.EQUIP,equipmentController);
        add(MsgId.USE_ITEM,useItemController);
        add(MsgId.SHOW_BAGS, showBagsController);
        add(MsgId.COMMON_ATTACK,commonAttackController);
        add(MsgId.LOCATION,locationController);
        add(MsgId.ENTER_INSTANCE, enterInstanceController);
        add(MsgId.SHOW_EQUIPMENT_BAR,showEquipmentController);
        add(MsgId.SHOW_PLAYER,showPlayerController);
        add(MsgId.REMOVE_EQUIP,removeEquipController);
        add(MsgId.BUY_GOODS,buyGoodsController);
        add(MsgId.SHOW_GOODS, showGoodsController);
        add(MsgId.WHISPER,whisperController);
        add(MsgId.PUBLIC_CHAT,publicChatController);
        add(MsgId.SEND_MAIL,sendMailController);
        add(MsgId.MAIL_LIST,mailListController);
        add(MsgId.GET_MAIL,getMailController);
        add(MsgId.PVP,pvpController);;
        add(MsgId.SKILL_PVP,skillPVPController);
    }




    private void add(MsgId msgId, IController controller) {
        controllerMapping.put(msgId, controller);
    }


    public IController get(int msgId) {
        // 通过int的msgId找到枚举的MsgId
        return controllerMapping.get(MsgId.find(msgId,MsgId.UNKNOWN));
    }
}
