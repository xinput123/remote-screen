package com.xinput.screen.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoteDesktop {

  public static SharePanel panel;
  private RemoteMouseListener remoteMouseListener;
  private RemoteKeyListener remoteKeyListener;
  private static String ip;

  public RemoteDesktop() {
    remoteMouseListener = new RemoteMouseListener();
    remoteKeyListener = new RemoteKeyListener();
    // 新建一个可视化的屏幕监控的窗口
    JFrame jFrame = new JFrame();
    // 设置窗口的大小
    jFrame.setSize(600, 600);
    // 是否显示
    jFrame.setVisible(true);
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // 设置窗口置顶
//    jFrame.setAlwaysOnTop(true);
    panel = new SharePanel();
    jFrame.add(panel);

    panel.addMouseListener(remoteMouseListener);
    panel.addMouseMotionListener(remoteMouseListener);
    panel.addMouseWheelListener(remoteMouseListener);
    panel.addKeyListener(remoteKeyListener);
  }

  public static void initSetUp() {
    final JFrame inputFrame = new JFrame();
    inputFrame.setLayout(new BorderLayout());
    JLabel ipLabel = new JLabel("IP address:");
    final JTextField ipTextField = new JTextField();
    JLabel resolutionLabel = new JLabel("resolution:");
    final JTextField resolutionTextField = new JTextField();
    JButton okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        ip = ipTextField.getText();
        inputFrame.setVisible(false);
        try {
          Client client = Client.getInstance();
          if (client.connectServer(ip)) {
            if (client != null) {
              // send a message to wake server up to send capture
              String message = String.format("%20s", "ACK");
              client.channel.writeAndFlush(message + '\n');
            }
            new RemoteDesktop();
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    JPanel inputPanel = new JPanel();
    inputPanel.add(ipLabel);
    inputPanel.add(ipTextField);
    inputPanel.add(resolutionLabel);
    inputPanel.add(resolutionTextField);
    inputPanel.setLayout(new GridLayout(2, 2, 0, 20));
    inputFrame.add(inputPanel, BorderLayout.NORTH);
    inputFrame.add(okButton, BorderLayout.SOUTH);
    inputFrame.setSize(300, 150);
    inputFrame.setTitle("SetUp");
    inputFrame.setVisible(true);
    inputFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    initSetUp();
  }
}
