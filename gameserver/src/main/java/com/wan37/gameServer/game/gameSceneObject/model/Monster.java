package com.wan37.gameServer.game.gameSceneObject.model;

import lombok.Data;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:26
 * @version 1.00
 * Description: 怪物
 */


@Data
public class Monster  extends SceneObject {






    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}  state:{4}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp(),this.getState()  );
    }
}
