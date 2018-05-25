package com.practice.springcloud.gateway;

import com.practice.springcloud.gateway.filter.ElapsedFilter;
import com.practice.springcloud.gateway.rate_limit.RateLimitByIpGatewayFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

import java.net.URI;
import java.time.Duration;

/**
 * 测试方法：get请求， header加Host参数，借用Postman工具
 * <p>
 * https://github.com/spring-cloud-samples/spring-cloud-gateway-sample
 * <p>
 * Created by luobd on 2018/5/5
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
//@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class GatewayApplication {
    public final static Logger logger = LogManager.getLogger("breakerLog");

    @RequestMapping("/hystrixfallback")
    public String hystrixfallback(ServerWebExchange exchange) {
        ServerWebExchange delegate = ((ServerWebExchangeDecorator) exchange).getDelegate();
        AbstractServerHttpRequest request = (AbstractServerHttpRequest) delegate.getRequest();
        URI uri = request.getURI();

        logger.warn(new MarkerManager.Log4jMarker("breaker"), "uri=[" + uri.toString() + "]");

        return "this is fallback";
    }

    @Autowired
    public ElapsedFilter elapsedFilter;


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                /*      .route("test_route", r -> r.path("/**")
                    HttpServletRequest           .filters(f -> f.hystrix(
                                      c -> c.setName("fallbackcmd").setFallbackUri("forward:/hystrixfallback")))
                              .uri("lb://say-hello"))
      */
/*                               .route("app_api_route", r -> r.path("/**")
                                       .filters(f -> f.hystrix(
                                               c -> c.setName("app-api-fallback").setFallbackUri("forward:/hystrixfallback")))
                                       .uri("lb://ufoto-app-api"))*/
//                        .uri("http://localhost:8070"))

                //===============

                .route("path_route", r -> r.path("/get")
                        .filters(f -> f.filter(elapsedFilter))
                        .uri("http://httpbin.org"))

                .route("throttle_route", r -> r.path("/throttle/uuid")
                        .filters(f -> f.stripPrefix(1)
                                .filter(new RateLimitByIpGatewayFilter(true, 2, 1, Duration.ofSeconds(2))))
                        .uri("http://httpbin.org"))
                .route("host_route", r -> r.host("*.myhost.org")
                        .uri("http://httpbin.org"))
                .route("rewrite_route", r -> r.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                        .uri("http://httpbin.org"))
                .route("hystrix_route", r -> r.host("*.hystrix.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd"))).uri("http://httpbin.org"))

                // 测试方法： localhost:8087/delay/1 ， 其中delay/1相当于指定ribbon超时为1s
                .route("hsytrix_fallback_route", r -> r.host("*.hystrixfallback.org")
                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")
                                .setFallbackUri("forward:/hystrixfallback")))
                        .uri("http://httpbin.org"))

                .route("auth_route", r -> r.path("/anything/**")
                        .uri("http://httpbin.org"))

                .route("limit_fluent_route", r -> r.path("/fluent/limit/**")
                        .filters(f -> f.stripPrefix(1).requestRateLimiter(c -> c.setRateLimiter(new RedisRateLimiter(2, 2))))
                        .uri("http://httpbin.org"))

                .route("websocke_route", r -> r.path("/echo").uri("ws://localhost:9000"))

                .build();

    }

/*
    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.httpBasic().and().authorizeExchange().pathMatchers("/anything/**")
                .authenticated().anyExchange().permitAll().and().build();
    }

    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user")
                .password("password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }
*/


    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
