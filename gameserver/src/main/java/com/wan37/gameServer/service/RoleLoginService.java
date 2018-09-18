package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.map.Position;
import com.wan37.gameServer.entity.role.Adventurer;
import com.wan37.gameServer.entity.role.Role;
import org.springframework.stereotype.Service;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 16:03
 * @version 1.00
 * Description: JavaLearn
 */
@Service
public class RoleLoginService {

    public final static Role adventurer = createRole();


    public static synchronized Role createRole() {
        // 创新新的冒险者
        Role role = new Adventurer("贪婪的冒险者",9999,9999);

        Position position = new Position(0,0);
        role.setPosition(position);
        return role;
    }

}
