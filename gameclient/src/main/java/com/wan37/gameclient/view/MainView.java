package com.wan37.gameclient.view;



import com.wan37.common.entity.Cmd;
import com.wan37.common.proto.CmdProto;
import com.wan37.gameclient.GameClient;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



@Slf4j
public class MainView extends JFrame {

    public static final JTextArea output = new JTextArea();
    private static final JScrollPane jsp1 = new JScrollPane(output);
    private static final JTextArea input = new JTextArea();

    public MainView() {
        this.setLayout(null);

        output.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 16));
        output.setLineWrap(true);

        input.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
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


                    String[] array = text.split("\\s+");
                    Cmd msgId = Cmd.findByCommand(array[0], Cmd.UNKNOWN);

                    CmdProto.Cmd cmd = CmdProto.Cmd.newBuilder()
                            .setMgsId(msgId.getMsgId())
                            .setContent(text).build();
                    GameClient.channel.writeAndFlush(cmd);
                    input.setText("");
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

        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp1.setBounds(0, 0, 1368, 680);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //在文本框上添加滚动条
        JScrollPane jsp2 = new JScrollPane(input);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp2.setBounds(0, 690, 1368, 90);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //把滚动条添加到容器里面
        this.add(jsp1);
        this.add(jsp2);
        this.setSize(1440, 780);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
