package com.wan37.gameserver.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:46
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
public final class FileUtil {

    public static File getFile(String fileName) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:"+fileName);
        } catch (FileNotFoundException e) {
            log.debug("文件读取错误");
            e.printStackTrace();
        }
        if (Objects.isNull(file)) {
            log.debug("文件为null");
        }
        return file;
    }

    public static String getStringPath(String fileName) {
        return  fileName;
    }

}
