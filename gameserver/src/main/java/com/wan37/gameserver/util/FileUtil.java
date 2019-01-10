package com.wan37.gameserver.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:46
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
public final class FileUtil {

    public static File getFile(String fileName) {
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        /*
         getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource(fileName);
        /*
         * url.getFile() 得到这个文件的绝对路径
         */
        if (url == null)
            return null;
        return new File(url.getFile());
    }

    public static String getStringPath(String fileName) {
        File file = getFile(fileName);
        if (file == null)
            return null;
        return  file.getAbsolutePath();
    }

}
