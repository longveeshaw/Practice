package com.practice.netty.server.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author Luo Bao Ding
 * @since 2018/5/21
 */
public class SimpleTimeDecoder extends ReplayingDecoder<Void> {

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readBytes(4));
    }
}
