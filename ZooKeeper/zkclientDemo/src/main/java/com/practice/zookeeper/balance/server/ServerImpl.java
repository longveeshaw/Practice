package com.practice.zookeeper.balance.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class ServerImpl implements Server {

    private ServerNodeData serverNodeData;

    private ZkClient zkClient;

    private String serverDirNodePath;

    private String currentNodePath;

    private RegisterProvider registerProvider;

    private String zkAddress;

    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture channelFuture;

    private transient boolean bound;

    public ServerImpl(String zkAddress, String serverDirNodePath, ServerNodeData serverNodeData) {
        this.zkAddress = zkAddress;
        this.serverDirNodePath = serverDirNodePath;
        this.serverNodeData = serverNodeData;

        this.zkClient = new ZkClient(zkAddress, 5000, 5000, new SerializableSerializer());
        this.registerProvider = new DefaultRegisterProvider();
        this.currentNodePath = serverDirNodePath + "/" + serverNodeData.getHost() + ":" + serverNodeData.getPort();

    }

    private void initRunning() {
        this.registerProvider.register(new RegisterContext(zkClient, currentNodePath, serverNodeData));
    }

    public void bind() {
        if (bound) {
            return;
        }
        initRunning();
        System.out.println(serverNodeData.getHost() + ":" + serverNodeData.getPort() + " start binding...");

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler(new DefaultUpdateBalanceProvider(currentNodePath, zkClient)));

                    }
                });

        try {
            channelFuture = serverBootstrap.bind(serverNodeData.getPort()).sync();
            System.out.println(serverNodeData.getHost() + ":" + serverNodeData.getPort() + " bound!");
            bound = true;
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
