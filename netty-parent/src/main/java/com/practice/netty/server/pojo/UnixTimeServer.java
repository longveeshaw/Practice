package com.practice.netty.server.pojo;

import com.practice.netty.server.time.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * test:
 * $ rdate -o <port> -p <host>
 *
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class UnixTimeServer {
    private static int port = 8181;

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //======== UnixTimeEncoder 要放前面，越前面越后执行
//                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO), new UnixTimeEncoder(), new UnixTimeServerHandler());
//                            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO), new SimpleUnixTimeEncoder(), new UnixTimeServerHandler());
                            ch.pipeline().addLast("1", new UnixTimeServerHandler());//加名字来指定顺序
                            ch.pipeline().addLast("2", new SimpleUnixTimeEncoder());
                            ch.pipeline().addLast("3", new LoggingHandler(LogLevel.INFO));
                            //===========
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.bind(port).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
