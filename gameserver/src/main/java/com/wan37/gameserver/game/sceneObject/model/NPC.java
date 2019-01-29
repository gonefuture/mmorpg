package com.wan37.gameserver.game.sceneObject.model;



import com.wan37.gameserver.model.Creature;
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
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}  {4}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp(), this.getState()==-1?"死亡":"存活");
    }
}
