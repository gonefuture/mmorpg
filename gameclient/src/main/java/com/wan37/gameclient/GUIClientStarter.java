package com.wan37.gameclient;

import com.wan37.gameclient.view.MainView;


/**
 * 客户端
 */
public class GUIClientStarter {

    public static final MainView mainView = new MainView();

    public static void main(String[] args) throws Exception {
        GameClient.main("".split(""));
    }
}
