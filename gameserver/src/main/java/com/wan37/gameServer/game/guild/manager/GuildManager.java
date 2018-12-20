package com.wan37.gameServer.game.guild.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.guild.model.Guild;
import com.wan37.mysql.pojo.entity.TGuild;
import com.wan37.mysql.pojo.entity.TGuildExample;
import com.wan37.mysql.pojo.mapper.TGuildMapper;

import lombok.extern.slf4j.Slf4j;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 10:17
 * @version 1.00
 * Description: 公会管理
 */

@Slf4j
public class GuildManager {

    @Resource
    private static TGuildMapper tGuildMapper;



    private static Cache<Integer, Guild> guildCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> log.info(notification.getKey() + "公会被移除，原因是" + notification.getCause())
            ).build();


    static {
        // 从数据库加载公会数据
        List<TGuild> tGuildList = tGuildMapper.selectByExample(null);
        tGuildList.forEach(
                tGuild -> {
                    Guild guild = new Guild();
                    guild.setId(tGuild.getId());
                    guild.setName(tGuild.getName());
                    guild.setLevel(tGuild.getLevel());
                    guild.setMember(tGuild.getMember());
                    guild.setWarehouse(tGuild.getWarehouse());
                    loadMember(guild);
                    loadWarehouse(guild);

                    guildCache.put(guild.getId(),guild);
                    log.info("公会数据加载完毕");
                }
        );
    }



    public  static  Guild getGuild(Integer guildId) {
        return guildCache.getIfPresent(guildId);
    }


    public static void  putGuild(Integer guildId, Guild guild) {
        guildCache.put(guildId, guild);
    }


    /**
     *  加载公会成员
     * @param guild 公会
     */
    private static void loadMember(Guild guild) {
        if (Strings.isNullOrEmpty(guild.getWarehouse())) {
            Map<Long,Player> memberMap =  JSON.parseObject(guild.getMember(),
                    new TypeReference<Map<Long,Player>>(){});
            guild.setMemberMap(memberMap);
        }
    }

    /**
     *  加载公会仓库
     * @param guild 公会
     */
    private static void loadWarehouse(Guild guild) {

        if (Strings.isNullOrEmpty(guild.getWarehouse())) {
            Map<Integer,Item> wareHouseMap =  JSON.parseObject(guild.getWarehouse(),
                    new TypeReference<Map<Integer,Item>>(){});
            guild.setWarehouseMap(wareHouseMap);
        }
    }


    /**
     *  持久化公会
     * @param guild 公会
     */
    public void saveGuild(Guild guild) {
        guild.setMember(JSON.toJSONString(guild.getMemberMap()));
        guild.setWarehouse(JSON.toJSONString(guild.getWarehouseMap()));

        tGuildMapper.insert(guild);
    }




}
