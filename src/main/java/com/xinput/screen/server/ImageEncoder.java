package com.xinput.screen.server;

import com.xinput.screen.share.CaptureImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ImageEncoder extends MessageToByteEncoder<CaptureImage> {
  @Override
  protected void encode(ChannelHandlerContext ctx, CaptureImage captureImage, ByteBuf byteBuf) throws Exception {
    byteBuf.writeInt(captureImage.getLength());
    byteBuf.writeBytes(captureImage.getContent());
  }
}
