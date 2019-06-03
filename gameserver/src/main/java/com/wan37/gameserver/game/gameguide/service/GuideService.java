package com.wan37.gameserver.game.gameguide.service;

import com.wan37.common.entity.Cmd;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/22 16:46
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class GuideService {


    public List<String> allCmd() {
        return Arrays.stream(Cmd.values())
                .map( cmd -> MessageFormat.format("{0}          描述：{1}",
                        cmd.getCommand(), Optional.ofNullable(cmd.getExplain()).orElse(" ")))
                .collect(Collectors.toList());
    }


}
