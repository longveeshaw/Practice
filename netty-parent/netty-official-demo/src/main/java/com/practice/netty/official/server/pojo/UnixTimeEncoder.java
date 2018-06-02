package com.practice.netty.official.server.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class UnixTimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("UnixTimeEncoder.write");
        ByteBuf buffer = ctx.alloc().buffer(4);
        buffer.writeInt((int)((UnixTime) msg).value());
        ctx.write(buffer, promise);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
