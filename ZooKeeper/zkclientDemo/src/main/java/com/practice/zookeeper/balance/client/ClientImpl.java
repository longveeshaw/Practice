package com.practice.zookeeper.balance.client;

import com.practice.zookeeper.balance.server.ServerNodeData;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientImpl implements Client {

    private BalanceProvider<ServerNodeData> balanceProvider;

    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap=new Bootstrap();
    private Channel channel;

    private String clientName;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientImpl(BalanceProvider<ServerNodeData> balanceProvider,String clientName) {
        this.balanceProvider = balanceProvider;
        this.clientName=clientName;
    }

    public void connect() {
        try {
            ServerNodeData serverNodeData = this.balanceProvider.getBalanceItem();

            System.out.println(clientName + " calculated out balance server: " + serverNodeData);

            System.out.println(clientName + " starts connecting to " + serverNodeData.getAddress());
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(serverNodeData.getHost(), serverNodeData.getPort()).syncUninterruptibly();
            this.channel = channelFuture.channel();

            System.out.println(clientName + " successfully connected to " + serverNodeData.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(clientName + " connecting failed: " + e.getMessage());
        }

    }

    public void disconnect() {
        try {
            if (channel != null) {
                channel.close().syncUninterruptibly();
            }

            group.shutdownGracefully();
            group=null;
            logger.debug(clientName+" disconnected");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

        }

    }
}
