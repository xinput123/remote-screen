package com.xinput.screen.client;

import com.xinput.screen.util.IpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoteScreen {

  public static SharePanel panel;
  private RemoteMouseListener remoteMouseListener;
  private RemoteKeyListener remoteKeyListener;
  private static String ip;

  public RemoteScreen() {
    remoteMouseListener = new RemoteMouseListener();
    remoteKeyListener = new RemoteKeyListener();
    // initial frame
    JFrame frame = new JFrame();
    panel = new SharePanel();
    frame.add(panel);
    frame.setSize(1024, 1024);
    frame.setTitle("远程桌面控制");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    panel.addMouseListener(remoteMouseListener);
    panel.addMouseMotionListener(remoteMouseListener);
    panel.addMouseWheelListener(remoteMouseListener);
    panel.addKeyListener(remoteKeyListener);
  }

  public static void initSetUp() {
    // 新建一个可视化的请求窗口
    final JFrame inputFrame = new JFrame();
    // 设置窗口大小不可变
    inputFrame.setResizable(false);
    inputFrame.setLayout(new BorderLayout());
    // 创建一个标签
    JLabel ipLabel = new JLabel("Host IP");
    final JTextField ipTextField = new JTextField("192.168.8.163");
    // 创建一个按钮
    JButton okButton = new JButton("Connect");


    // 按钮事件监听
    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ip = ipTextField.getText();
        if (!IpUtils.check(ip)) {
          okButton.setText("IP不合法,请输入正确的IP");
          return;
        }
        inputFrame.setVisible(false);
        try {
          Client client = Client.getInstance();
          if (client.connectServer(ip)) {
            if (client != null) {
              // send a message to wake server up to send capture
              String message = String.format("%20s", "ACK");
              client.channel.writeAndFlush(message + '\n');
            }
            new RemoteScreen();
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    JPanel inputPanel = new JPanel();
    inputPanel.add(ipLabel);
    inputPanel.add(ipTextField);

    inputPanel.setLayout(new GridLayout(1, 2, 2, 10));
    inputFrame.add(inputPanel, BorderLayout.NORTH);
    inputFrame.add(okButton, BorderLayout.SOUTH);
    inputFrame.setSize(300, 150);
    inputFrame.setTitle("远程桌面连接");
    inputFrame.setVisible(true);
    // 屏幕居中显示
    inputFrame.setLocationRelativeTo(null);
    inputFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
