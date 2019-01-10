package com.wan37.gameserver.game.skills.model;


import com.wan37.gameserver.game.sceneObject.model.Monster;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/24 11:58
 * @version 1.00
 * Description: 召唤兽
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class Pet extends Monster {


    private Integer petId;






}
