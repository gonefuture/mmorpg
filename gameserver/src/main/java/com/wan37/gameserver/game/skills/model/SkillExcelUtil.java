package com.wan37.gameserver.game.skills.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 22:15.
 *  说明：
 */

import com.wan37.gameserver.util.excel.ReadExcelByEntity;

/**
 * <pre> </pre>
 */
public class SkillExcelUtil extends ReadExcelByEntity<Skill> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public SkillExcelUtil(String filepath) {
        super(filepath);
    }
}
