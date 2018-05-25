package com.practice.netty.server.time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class TimeClient {
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
//                            ch.pipeline().addLast(new TimeClientHandler()); //problem: may cause data fragmentation issue
//                            ch.pipeline().addLast(new SynthesizedTimeClientHandler());// solution 1
//                            ch.pipeline().addLast(new TimeDecoder(), new SynthesizedTimeClientHandler());// solution 2
                            ch.pipeline().addLast(new SimpleTimeDecoder(), new SynthesizedTimeClientHandler());// solution 2 , simple way


                        }
                    }).connect(remoteHost, remotePort)
                    .sync().channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
