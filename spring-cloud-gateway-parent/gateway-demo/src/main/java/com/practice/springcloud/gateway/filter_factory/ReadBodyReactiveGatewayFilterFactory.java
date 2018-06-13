package com.practice.springcloud.gateway.filter_factory;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.HttpMessageWriterResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.*;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ResourceHttpMessageWriter;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.MultipartHttpMessageWriter;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.method.annotation.RequestPartMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Luo Bao Ding
 * @since 2018/6/11
 */
@Component
public class ReadBodyReactiveGatewayFilterFactory extends AbstractGatewayFilterFactory implements ApplicationContextAware, InitializingBean {
    private BindingContext bindingContext = new BindingContext();

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private ConfigurableApplicationContext applicationContext;
    private ReactiveAdapterRegistry reactiveAdapterRegistry;
    private List<HttpMessageReader<?>> messageReaders;
    /**
     * 内问有 getMessageReaders()
     */
    private RequestPartMethodArgumentResolver requestPartMethodArgumentResolver;
//    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            try {

//                =======================

               /*
                Method multipart = ReadBodyReactiveGatewayFilterFactory.class.getDeclaredMethod("multipart", String.class, String.class);

                MethodParameter uidParam = new MethodParameter(multipart, 0);
//                uidParam.initParameterNameDiscovery(this.parameterNameDiscoverer);//@RequestPart中使用name,则这行可去掉

Mono<Object> uidMono = requestPartMethodArgumentResolver.resolveArgument(uidParam, bindingContext, exchange);
                return uidMono.flatMap(o -> {
                    String uid = (String) o;
                    System.out.println("uid = " + uid);
                    if (StringUtils.isNotBlank(uid)) {

                        return chain.filter(exchange);
                    } else {
                        return Mono.empty();
                    }
                });*/


                //=======================
               /* Flux<List<Part>> map = exchange.getMultipartData()
                        .map(Map::entrySet).flatMapMany(entry -> {
                            return Flux.fromIterable(entry);
                        }).map(Map.Entry::getValue);*/
                // TODO: 2018/6/12

                ServerHttpRequest request = exchange.getRequest();
                /* Mono<MultiValueMap<String, Part>> multipartData = */
                Mono<MultiValueMap<String, Part>> multipartData = exchange.getMultipartData();

                MultipartHttpMessageWriter multipartHttpMessageWriter = new MultipartHttpMessageWriter(Arrays.asList(
                        new EncoderHttpMessageWriter<>(CharSequenceEncoder.textPlainOnly()),
                        new ResourceHttpMessageWriter()
                ));// exception: "No suitable writer found for part: uid"


                HttpMessageWriterResponse message = new HttpMessageWriterResponse(exchange.getResponse().bufferFactory());
                Mono write = multipartHttpMessageWriter.write(multipartData, ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class), MediaType.MULTIPART_FORM_DATA,
                        message, Collections.emptyMap());

                return multipartData.flatMap(map -> Mono.just(map.get("sign")))
                        .flatMap(parts -> Mono.just(parts.get(0))).flatMap(part -> {
                            FormFieldPart formFieldPart = (FormFieldPart) part;
                            String sign = formFieldPart.value();
                            if (StringUtils.isBlank(sign)) {
                                throw new RuntimeException("========== sign is blank! ==========");
                            } else {
                                ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                        exchange.getRequest()) {
                                    @Override
                                    public HttpHeaders getHeaders() {
                                        HttpHeaders httpHeaders = new HttpHeaders();
                                        httpHeaders.putAll(super.getHeaders());
                                        httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                        return httpHeaders;
                                    }

                                    @Override
                                    public Flux<DataBuffer> getBody() {
                                        ReplayProcessor<Object> replayProcessor = ReplayProcessor.create();
                                        replayProcessor.join()
                                        // TODO: 2018/6/12  "Only one connection receive subscriber allowed."
                                        return Flux.from(request.getBody());
//                                        return request.getBody();
//                                        return Flux.from(write.defaultIfEmpty("")).flatMap(o -> message.getBody());
//
                                    }
                                };

                                return chain.filter(exchange.mutate().request(decorator).build());
//                                return chain.filter(exchange);
                            }
                        }).doOnError(Throwable::printStackTrace);


//                ==================

            } catch (Exception e) {
                e.printStackTrace();
            }

//            exchange.getMultipartData();


            throw new RuntimeException("========== custom exception ==========");
        };
    }

    public void multipart(@RequestPart(name = "uid") String uid,//要加上name,否则要多写很多代码,如要uidParam.initParameterNameDiscovery,见上
                          @RequestPart(name = "sign") String sign) {
    }


    @Autowired
    public void setRequestMappingHandlerAdapter(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        messageReaders = requestMappingHandlerAdapter.getMessageReaders();
        reactiveAdapterRegistry = requestMappingHandlerAdapter.getReactiveAdapterRegistry();
        requestPartMethodArgumentResolver = new RequestPartMethodArgumentResolver(messageReaders, reactiveAdapterRegistry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
