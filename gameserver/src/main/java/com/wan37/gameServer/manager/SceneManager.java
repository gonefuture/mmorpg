package com.wan37.gameServer.manager;

import com.wan37.mysql.pojo.entity.Scene;
import com.wan37.mysql.pojo.mapper.SceneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 18:07
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SceneManager {
    @Resource
    private SceneMapper sceneMapper;


    @PostConstruct
    private void init() {
        List<Scene> sceneList = sceneMapper.selectByExample(null);
        for (Scene scene: sceneList) {
            CacheManager.put("scene:"+scene.getId(),scene);
        }
        log.info("场景资源加载进缓存完毕");
    }

}
