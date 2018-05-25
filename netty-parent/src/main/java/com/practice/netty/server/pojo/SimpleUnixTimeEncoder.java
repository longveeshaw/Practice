package com.practice.netty.server.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class SimpleUnixTimeEncoder  extends MessageToByteEncoder<UnixTime> {
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        out.writeInt((int)msg.value());
    }
}
