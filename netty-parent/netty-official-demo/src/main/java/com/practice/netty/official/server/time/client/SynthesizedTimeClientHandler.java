package com.practice.netty.official.server.time.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class SynthesizedTimeClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf frag = (ByteBuf) msg;
        buf.writeBytes(frag);
        frag.release();
        if (buf.readableBytes() >= 4) {
            long time = (buf.readUnsignedInt() - 2208988800L) * 1000L;
            Date date = new Date(time);
            System.out.println("date = " + date);
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
