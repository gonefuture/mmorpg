package com.wan37.gameServer.game.shop.service;


import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.shop.manager.ShopManager;
import com.wan37.gameServer.game.shop.model.Shop;
import com.wan37.gameServer.game.things.model.Things;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 10:40
 * @version 1.00
 * Description: 商店系统服务
 */

@Slf4j
@Service
public class ShopService {

    @Resource
    private ShopManager shopManager;

    @Resource
    private BagsService bagsService;


    public Msg buyGoods(Player player, Integer shopId, Integer goodsId) {
        Shop shop =  shopManager.get(shopId);
        Things things = shop.getGoodsMap().get(goodsId);
        if ( null == things)
            return new Msg(404,"该商店没有此物品");

        if (player.getMoney() < things.getPrice()) {
            return new Msg(401, "不好意思，你身上的钱不足以购买该物品");
        }

        // 唯一id用一个long类型存储，long类型有64位，我们使用它的低32位存储策划配置的物品表中的物品id，
        // 高32用来记录每获得一个物品就自增加1的序列order
        Long itemId =  ((new Date().getTime())<< 32) + (long)things.getId();
        Item item = new Item(itemId,2,0,things);

        log.debug("商店 shop {}",shop);

        if(bagsService.addItem(player,item)) {
            // 商品放入背包成功，扣钱
            player.setMoney(player.getMoney() - things.getPrice());
            return new Msg(200,"购买物品 "+things.getName()+" 成功", item);
        } else {
            return new Msg(402,"购买失败，可能是因为背包已满");
        }

    }



    public Map<Integer,Things> showGoods(Integer shopId) {

        Shop shop =  shopManager.get(shopId);
        return shop.getGoodsMap();
    }


}
