package com.xinput.screen.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class SharePanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private BufferedImage image;
  ByteArrayInputStream bais;

  // accept and handler byte array from ClientHandler
  public void display(byte[] imageByte) {
    bais = new ByteArrayInputStream(imageByte);
    // invoke paint()
    this.repaint();
  }

  // paint the capture on the panel
  public void paint(Graphics g) {
    try {
      image = ImageIO.read(bais);
      g.drawImage(image, 0, 0, this);
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }
}
