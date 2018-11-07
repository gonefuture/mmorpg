package com.wan37.gameServer.util.ProbabilityTool;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/7 17:06.
 *  说明：
 */

import java.util.Random;

/**
 * <pre> </pre>
 */
public final class ProbabilityUtil {

    public static boolean dropProbability(int chance) {
        Random random = new Random();
        return chance > random.nextDouble();
    }
}
