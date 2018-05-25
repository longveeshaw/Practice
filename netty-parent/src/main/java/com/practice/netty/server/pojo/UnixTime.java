package com.practice.netty.server.pojo;

import java.io.Serializable;
import java.util.Date;

public class UnixTime implements Serializable {

    private static final long serialVersionUID = 1912838479917310497L;

    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}