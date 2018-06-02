package com.practice.netty.official.server.pojo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class UnixTimeClient {
    private static int remotePort = 8181;
    private static String remoteHost = "localhost";

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // ============
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO), new UnixTimeDecoder(), new UnixTimeClientHandler());
                            // ================
                        }
                    }).connect(remoteHost, remotePort)
                    .sync().channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
