package com.wan37.gameserver.game.mail.service;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Msg;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.mysql.pojo.entity.TMail;
import com.wan37.mysql.pojo.entity.TMailExample;
import com.wan37.mysql.pojo.mapper.TMailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 12:01
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class MailService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private BagsService bagsService;

    @Resource
    private TMailMapper tMailMapper;



    /**
     *      发送邮件
     * @param player 玩家
     * @param target 收件人
     * @param bagIndex 背包物品格子索引
     * @return 结果
     */
    public Msg playerSendMail(Player player, Long target,
                        String subject, String content, Integer bagIndex) {
        Player targetPlayer = playerDataService.getOnlinePlayerById(target);
        Item item = bagsService.removeItem(player,bagIndex);

        sendMail(player.getId(),targetPlayer.getId(),subject,content,item);
        notificationManager.notifyPlayer(targetPlayer,MessageFormat.format("你收到一封来自 {0} 邮件 ",
                player.getName()));
        return new Msg(200,"邮件发送成功");
    }


    /**
     *     发送邮件
     * @param sender    发送者
     * @param receiver  接收者
     * @param subject
     * @param content
     * @param item
     */
    public void sendMail(Long sender, Long receiver,
                        String subject, String content, Item item) {
        TMail tMail = new TMail();
        tMail.setSubject(subject);
        tMail.setContent(content);
        tMail.setSender(sender);
        tMail.setReceiver(receiver);
        tMail.setHasRead(false);
        // 如果邮件，插入附件
        Optional.ofNullable(item).ifPresent( i -> tMail.setAttachment(JSON.toJSONString(i)));

        //持久化
        tMailMapper.insertSelective(tMail);
    }


    /**
     *  根据玩家id查找邮件列表
     * @param player 玩家
     * @return 邮件列表，可能为空
     */
    public List<TMail> mailList(Player player) {
        TMailExample tMailExample = new TMailExample();
        tMailExample.or().andReceiverEqualTo(player.getId());

        return tMailMapper.selectByExample(tMailExample);
    }


    /**
     *  获取邮件附件
     * @param player 玩家
     * @param targetMail 收取邮件的唯一id
     * @return 结果
     */
    public Msg getMail(Player player, Integer targetMail) {
        TMail tMail = tMailMapper.selectByPrimaryKey(targetMail);
        if (tMail.getReceiver().equals(player.getId())){
            String itemString = tMail.getAttachment();
            Item item = JSON.parseObject(itemString,Item.class);
            if (item == null) {
                return new Msg(500,"邮件并没有附件或者附件已经取走");
            }
            if (bagsService.addItem(player,item)) {
                // 更新邮件状态
                tMail.setAttachment(null);
                tMail.setHasRead(true);
                tMailMapper.updateByPrimaryKeySelective(tMail);
                return new Msg(200,"接收邮件成功，附件已经在你的背包了");
            } else {
                return new Msg(400,"接收收件被拒绝，可能是背包空间不足");
            }
        } else {
            return new Msg(404,"你的邮箱里并没有这封邮件");
        }
    }



}
