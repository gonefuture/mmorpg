package com.wan37.gameclient;

import com.wan37.gameclient.view.MainView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameClientApplication {

    public static final MainView mainView = new MainView();

    public static void main(String[] args) {

        SpringApplication.run(GameClientApplication.class, args);
        GameClient.main("".split(""));
    }

}
