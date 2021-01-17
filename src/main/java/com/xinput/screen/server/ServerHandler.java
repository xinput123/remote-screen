package com.xinput.screen.server;

import com.xinput.screen.domain.ShareImage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ServerHandler extends ChannelInboundHandlerAdapter {

  private BufferedImage image;
  private Robot robot;
  private Toolkit toolkit;
  private Rectangle rect;
  private ByteArrayOutputStream baos;
  private ShareImage shareImage;

  public ServerHandler() {
    try {
      // 创建一个机器人
      robot = new Robot();
      // 定义方法能够直接查询本机操作系统
      toolkit = Toolkit.getDefaultToolkit();
      // 获取屏幕的大小
      Dimension dm = toolkit.getScreenSize();

      // 指定坐标空间的区域
      rect = new Rectangle(0, 0, dm.width, dm.height);
      baos = new ByteArrayOutputStream();
      shareImage = new ShareImage();
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      String message = (String) msg;
      switch (message.substring(0, 20).trim()) {
        case "ACK":
          image = robot.createScreenCapture(rect);
          ImageIO.write(image, "jpg", baos);
          shareImage.setLength(baos.toByteArray().length);
          shareImage.setContent(baos.toByteArray());
          ctx.writeAndFlush(shareImage);
          baos.reset();
          break;
        case "moveTo":
          int x = Integer.valueOf(message.substring(20, 30).trim());
          int y = Integer.valueOf(message.substring(30).trim());
          robot.mouseMove(x, y);
          break;
        case "leftClick":
          robot.mousePress(InputEvent.BUTTON1_MASK);
          robot.mouseRelease(InputEvent.BUTTON1_MASK);
          break;
        case "rightClick":
          robot.mousePress(InputEvent.BUTTON3_MASK);
          robot.mouseRelease(InputEvent.BUTTON3_MASK);
          break;
        case "leftDown":
          robot.mousePress(InputEvent.BUTTON1_MASK);
          break;
        case "leftUp":
          robot.mouseRelease(InputEvent.BUTTON1_MASK);
          break;
        case "wheel":
          int wheelAmt = Integer.valueOf(message.substring(20, 30).trim());
          robot.mouseWheel(wheelAmt);
          break;
        case "keyType":
          int keycode = Integer.valueOf(message.substring(20, 30).trim());
          robot.keyPress(keycode);
          robot.keyRelease(keycode);
          break;
        case "keyDown":
          int keycode1 = Integer.valueOf(message.substring(20, 30).trim());
          robot.keyPress(keycode1);
          break;
        case "keyUp":
          int keycode2 = Integer.valueOf(message.substring(20, 30).trim());
          robot.keyRelease(keycode2);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    // cause.printStackTrace();
    System.err.println("An existing connection was forcibly closed by the remote host");
  }
}
