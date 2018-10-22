package com.wan37.gameServer.game.things.manager;

import com.wan37.gameServer.game.things.modle.ThingsExcel;
import com.wan37.gameServer.game.things.modle.ThingsExcelUtil;
import com.wan37.gameServer.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:33
 * @version 1.00
 * Description: mmorpg
 */
@Component
@Slf4j
public class ThingsManager {

    public ThingsManager() {
        String path = FileUtil.getStringPath("gameData/things.xlsx");
        log.debug(path);
        ThingsExcelUtil thingsExcelUtil = new ThingsExcelUtil(path);

        try {
            Map<Integer,ThingsExcel> thingsMap = thingsExcelUtil.getMap();
            log.debug("配置表数据"+thingsMap);
            for (ThingsExcel thingsExcel: thingsMap.values()) {
                //log.debug(thingsExcel.getName());
                //log.debug("thingsExcel"+thingsExcel);
            }
        } catch (Exception e) {
            log.error("物品配置表加载失败");
            throw new RuntimeException(e);
        }

    }
}
