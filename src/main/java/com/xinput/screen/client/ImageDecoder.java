package com.xinput.screen.client;

import com.xinput.screen.domain.ShareImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ImageDecoder extends ReplayingDecoder<Void> {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int length = in.readInt();

    byte[] content = new byte[length];
    in.readBytes(content);

    ShareImage shareImage = new ShareImage();
    shareImage.setLength(length);
    shareImage.setContent(content);

    out.add(shareImage);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    // cause.printStackTrace();
    System.err.println("An existing connection was forcibly closed by the remote host");
  }
}
