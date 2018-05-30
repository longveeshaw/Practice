package com.practice.zookeeper.balance.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

    public static final int BALANCE_STEP = 1;
    private final UpdateBalanceProvider updateBalanceProvider;

    public ServerHandler(DefaultUpdateBalanceProvider defaultUpdateBalanceProvider) {
        this.updateBalanceProvider = defaultUpdateBalanceProvider;

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("a channel connected");
        updateBalanceProvider.addBalance(BALANCE_STEP);
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("a channel disconnected");
        updateBalanceProvider.reduceBalance(BALANCE_STEP);

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
