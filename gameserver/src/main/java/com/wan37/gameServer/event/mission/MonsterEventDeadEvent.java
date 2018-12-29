package com.wan37.gameServer.event.mission;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 15:33
 * @version 1.00
 * Description: 怪物死亡的事件
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class MonsterEventDeadEvent  extends Event {


    private Player player;
    private Monster target;
    private GameScene gameScene;
    private Integer damage;




}
