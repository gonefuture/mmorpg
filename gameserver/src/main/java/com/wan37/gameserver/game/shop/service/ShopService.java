package com.wan37.gameserver.game.shop.service;


import com.wan37.common.entity.Msg;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.shop.manager.ShopManager;
import com.wan37.gameserver.game.shop.model.Shop;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
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

    @Resource
    private ThingInfoService thingInfoService;


    public Msg buyGoods(Player player, Integer shopId, Integer goodsId) {
        Shop shop =  shopManager.get(shopId);
        ThingInfo thingInfo = shop.getGoodsMap().get(goodsId);
        if ( null == thingInfo)
            return new Msg(404,"该商店没有此物品");

        if (player.getMoney() < thingInfo.getPrice()) {
            return new Msg(401, "不好意思，你身上的钱不足以购买该物品");
        }

        Item item = new Item(thingInfoService.generateItemId(),2, thingInfo);

        log.debug("商店 shop {}",shop);

        if(bagsService.addItem(player,item)) {
            // 商品放入背包成功，扣钱
            player.setMoney(player.getMoney() - thingInfo.getPrice());
            return new Msg(200,"购买物品 "+ thingInfo.getName()+" 成功", item);
        } else {
            return new Msg(402,"购买失败，可能是因为背包已满");
        }

    }



    public Map<Integer, ThingInfo> showGoods(Integer shopId) {

        Shop shop =  shopManager.get(shopId);
        return shop.getGoodsMap();
    }


}
