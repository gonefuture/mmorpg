package com.wan37.gameServer.util.excel;

import com.google.common.cache.Cache;
import com.wan37.gameServer.entity.IExcel;
import com.wan37.gameServer.game.things.modle.ThingsExcelUtil;
import com.wan37.gameServer.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 18:00
 * @version 1.00
 * Description: mmorpg
 */

public final class ExcelUtil<K,V>{
    private Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public final void  loadExcelToCache(String fileName, ReadExcelByEntity<IExcel> readExcelByEntity, Cache<K,V> cache,Class<V> clazz) {
        String path = FileUtil.getStringPath(fileName);
        ReadExcelByEntity excelUtil = new ThingsExcelUtil(path);

        try {
            Map<Integer,IExcel> map = excelUtil.getMap();
            for (IExcel excel : map.values()) {
                V o = clazz.newInstance();
                BeanUtils.copyProperties(excel,o);
                cache.put((K)excel.getKey(),o);
            }
            log.debug("游戏配置配置表数据 {}",map);
            log.info("游戏配置表加载完毕");

        } catch (Exception e) {
            log.error("物品配置表加载失败");
            throw new RuntimeException(e);
        }
    }


}
