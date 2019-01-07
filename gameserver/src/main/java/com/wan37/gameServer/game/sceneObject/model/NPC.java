package com.wan37.gameServer.game.sceneObject.model;



import com.wan37.gameServer.model.Creature;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:25
 * @version 1.00
 * Description: NPC
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NPC  extends SceneObject  implements Creature {



    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3} state:{4}" +
                        ""
                ,this.getId(),this.getName(), this.getHp(), this.getMp(), this.getState());
    }
}
