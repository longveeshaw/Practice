package com.practice.netty.folk.example.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * https://waylau.gitbooks.io/essential-netty-in-action/GETTING%20STARTED/Writing%20an%20echo%20client.html
 */
@ChannelHandler.Sharable                                //1
public class EchoClientHandler extends
        SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", //2
        CharsetUtil.UTF_8));
    }
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received: " + msg.toString(CharsetUtil.UTF_8));    //3

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {                    //4
        cause.printStackTrace();
        ctx.close();
    }


}