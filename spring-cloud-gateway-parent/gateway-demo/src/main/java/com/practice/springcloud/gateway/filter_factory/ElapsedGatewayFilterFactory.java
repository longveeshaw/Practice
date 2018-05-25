package com.practice.springcloud.gateway.filter_factory;

import com.practice.springcloud.gateway.filter.ElapsedFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.tuple.Tuple;

/**
 * @author Luo Bao Ding
 * @since 2018/5/14
 */
@Component
public class ElapsedGatewayFilterFactory implements GatewayFilterFactory/*,Ordered*/ {

    @Autowired
    public ElapsedFilter elapsedFilter;

    @Override
    public GatewayFilter apply(Tuple args) {
        return elapsedFilter;
    }
/*
// 没用
    @Override
    public int getOrder() {
        return 33;
    }*/
}
