package com.xinput.screen.domain;

/**
 * 截图
 */
public class ShareImage {

  private int length;

  private byte[] content;

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
