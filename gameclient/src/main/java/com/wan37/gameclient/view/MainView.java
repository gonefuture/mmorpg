package com.wan37.gameclient.view;



import com.wan37.common.entity.Cmd;
import com.wan37.common.proto.CmdProto;
import com.wan37.gameclient.GameClient;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class MainView extends JFrame {

    public static final JTextArea output = new JTextArea();
    private static final JTextArea input = new JTextArea();


    public static final JTextArea information= new JTextArea();
    public static final JTextArea equipment = new JTextArea();
    public static final JTextArea bag = new JTextArea();

    public MainView() {
        this.setLayout(null);

        output.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
        output.setLineWrap(true);

        input.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        input.setLineWrap(true);
        input.setText("请在此处输入命令");

        information.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        information.setLineWrap(true);
        information.setText("角色信息：\n 角色尚未登陆");


        equipment.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        equipment.setLineWrap(true);
        equipment.setText("装备栏：\n 角色尚未登陆");

        bag.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        bag.setLineWrap(true);
        bag.setText("背包栏：\n 角色尚未登陆");



        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                // NOOP
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    String text = input.getText().replaceAll("\n", "");
                    output.append(text + "\n");

                    System.out.println("客户端输入： "+text);
                    String[] array = text.split("\\s+");
                    Cmd msgId = Cmd.findByCommand(array[0], Cmd.UNKNOWN);

                    CmdProto.Cmd cmd = CmdProto.Cmd.newBuilder()
                            .setMgsId(msgId.getMsgId())
                            .setContent(text).build();

                    GameClient.channel.writeAndFlush(cmd);
                    input.setText("");

                    // 刷新武器栏和登陆栏
                    CmdProto.Cmd informationCmd  = CmdProto.Cmd.newBuilder()
                            .setMgsId(Cmd.SHOW_PLAYER.getMsgId())
                            .setContent("player").build();
                    GameClient.channel.writeAndFlush(informationCmd);

                    CmdProto.Cmd equipmentCmd  = CmdProto.Cmd.newBuilder()
                            .setMgsId(Cmd.SHOW_EQUIPMENT_BAR.getMsgId())
                            .setContent("equip_bar").build();
                    GameClient.channel.writeAndFlush(equipmentCmd);

                    CmdProto.Cmd bagsCmd  = CmdProto.Cmd.newBuilder()
                            .setMgsId(Cmd.SHOW_BAGS.getMsgId())
                            .setContent("bags").build();
                    GameClient.channel.writeAndFlush(bagsCmd);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    input.setCaretPosition(0);
                }
            }
        });


        JScrollPane displayBox = new JScrollPane(output);

        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        displayBox.setBounds(0, 0, 1000, 680);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        displayBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //在文本框上添加滚动条
        JScrollPane inputBox = new JScrollPane(input);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        inputBox.setBounds(0, 690, 1000, 90);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        inputBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 角色信息栏
        JScrollPane informationBar = new JScrollPane(information);
        informationBar.setBounds(1024,0,1368, 380);

        // 装备栏
        JScrollPane equipmentBar = new JScrollPane(equipment);
        equipmentBar.setBounds(1024, 400, 1368, 200);


        // 背包物品栏

        JScrollPane bagsBar = new JScrollPane(bag);
        bagsBar.setBounds(1024, 600, 1368, 200);


        //把滚动条添加到容器里面
        this.add(displayBox);
        this.add(inputBox);
        this.add(informationBar);
        this.add(equipmentBar);
        this.add(bagsBar);


        this.setSize(1440, 860);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
