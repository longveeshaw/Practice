package com.practice.spring.webflux.operate_demo.copy_body;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toWebHandler;
import static org.springframework.web.server.adapter.WebHttpHandlerBuilder.webHandler;

/**
 * @author Luo Bao Ding
 * @since 2018/6/8
 */
public class CopyBodyController {
//    @Bean
    public HttpHandler myRoute(MyHandler handler) {
        final RouterFunction<ServerResponse> routerFunction =
                route(POST("/myResource"), handler::persistNotification);
        return webHandler(toWebHandler(routerFunction))
                .filter(new TracingFilter())
                .build();
    }
}
