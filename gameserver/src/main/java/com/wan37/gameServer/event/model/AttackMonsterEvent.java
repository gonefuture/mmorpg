package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 14:56
 * @version 1.00
 * Description: mmorpg
 */

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
public class AttackMonsterEvent extends Event {

    private Player player;
    private Monster target;
    private GameScene gameScene;
    private Integer damage;


}
