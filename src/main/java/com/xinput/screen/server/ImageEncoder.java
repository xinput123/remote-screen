package com.xinput.screen.server;

import com.xinput.screen.domain.ShareImage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author <a href="mailto:xinput.xx@gmail.com">xinput</a>
 * @version v1.0
 * @date 2021/1/17 15:51
 * @description
 */
public class ImageEncoder extends MessageToByteEncoder<ShareImage> {
  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, ShareImage image, ByteBuf byteBuf) throws Exception {
    byteBuf.writeInt(image.getLength());
    byteBuf.writeBytes(image.getContent());
  }
}
