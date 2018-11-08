package com.wan37.gameServer.game.user.service;




import com.wan37.gameServer.model.User;
import com.wan37.gameServer.manager.cache.PlayerListCacheMgr;
import com.wan37.gameServer.manager.cache.UserCacheMgr;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.entity.TPlayerExample;
import com.wan37.mysql.pojo.entity.TUser;

import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import com.wan37.mysql.pojo.mapper.TUserMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 18:02
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Service
public class UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private TPlayerMapper tPlayerMapper;

    @Resource
    private UserCacheMgr userCacheMgr;

    @Resource
    private PlayerListCacheMgr playerListCacheMgr;



    /*
     * 判断用户id和密码是否正确
     */
    public boolean login(Long userId, String password, ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        User userCache = userCacheMgr.get(channelId);
        // 缓存中不存在用户,或者当前连接在线的不是现在要登陆的账号，所以缓存中不存在用户。
        if (userCache == null || !userCache.getId().equals(userId)) {
            TUser tUser= tUserMapper.selectByPrimaryKey(userId);
            if (tUser == null) {
                // 数据库找不到用户，
                return false;
            } else {
                // 密码校对成功，登陆成功
                if (tUser.getPassword().equals(password)) {
                    // 注销之前登陆的用户
                    logoutUser(userId);

                    User user = new User();
                    BeanUtils.copyProperties(tUser,user);
                    user.setChannelId(channelId);
                    userCacheMgr.put(channelId,user);
                    return true;
                } else {
                    // 用户名或密码错误，或没注册
                    return false;
                }
            }
        } else {
            // 缓存中有用户存在,且检验密码成功
            if (userCache.getPassword().equals(password)) {

                //  替换channelId和用户缓存的联系,关闭连接
                logoutUser(userId);
                // 替换channelId和用户缓存的联系
                userCacheMgr.put(channelId,userCache);
                return true;
            } else {
                return false;
            }
        }
    }



    /**
     *  根据用户id产找游戏角色列表
     */
    public List<TPlayer> findPlayers(Long userId) {
        //
        List<TPlayer> tPlayerListCache = playerListCacheMgr.get(userId);

        if (tPlayerListCache == null) {
            // 通过t_player表的player_id 查找与之相关的角色
            TPlayerExample tPlayerExample = new TPlayerExample();
            tPlayerExample.createCriteria().andUserIdEqualTo(userId);
            List<TPlayer> tPlayerList = tPlayerMapper.selectByExample(tPlayerExample);
            log.debug("查出的角色列表： "+ tPlayerList);

            // 将角色列表加入缓存
            playerListCacheMgr.put(userId, tPlayerList);
            return tPlayerList;
        }

        return tPlayerListCache;
    }

    /**
     * 判断用户是否在线
     */
    public boolean isUserOnline(String channelId) {
        User user = userCacheMgr.get(channelId);
        return user != null;
    }



    public void logoutUser(long userId) {

        ChannelHandlerContext ctx = userCacheMgr.getCtxById(userId);
        if (ctx != null) {
            userCacheMgr.removeUser(ctx.channel().id().asLongText());
            // 关闭连接
            ctx.close();
        }

    }



}