package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.map.Position;
import com.wan37.gameServer.entity.role.Adventurer;
import com.wan37.gameServer.entity.role.Role;
import org.springframework.stereotype.Service;

import java.util.Random;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 16:03
 * @version 1.00
 * Description: JavaLearn
 */
@Service
public class RoleLoginService {




    public static synchronized Role createRole() {

        Random r = new Random();
        // 创新新的冒险者
        Role role = new Adventurer(r.nextLong(),"贪婪的冒险者",9999,9999);

        Position position = new Position(0,0);
        role.setPosition(position);
        return role;
    }

}
