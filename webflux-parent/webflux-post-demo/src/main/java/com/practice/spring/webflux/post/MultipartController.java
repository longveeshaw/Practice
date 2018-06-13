package com.practice.spring.webflux.post;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.http.codec.FormHttpMessageWriter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ResourceHttpMessageWriter;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.MultipartHttpMessageWriter;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luo Bao Ding
 * @since 2018/6/11
 */
@RestController
//@Controller
public class MultipartController implements InitializingBean {

    private WebClientAutoConfiguration webClientAutoConfiguration;
    private List<HttpMessageWriter<?>> httpMessageWriters;
    private MultipartHttpMessageWriter multipartHttpMessageWriter;


    @PostMapping(value = "/post")
    public @ResponseBody
    Object uploadChatImg(ServerWebExchange exchange,
                         @RequestBody Flux<Part> parts,
                         @RequestPart("image") FilePart image,
                         @RequestPart("uid") String uid,
                         @RequestPart("sign") String sign) {
        String filename = image.filename();
        System.out.println("filename = " + filename);
//        return exchange.getMultipartData().map(map -> map.get("image"));// note: 不可行
        return "filename = [" + filename + "], uid = [" + uid + "], sign = [" + sign + "]";
    }

    /**
     * fail:
     * "error": "Internal Server Error",
     * "message": "No suitable writer found for part: uid"
     */
    @PostMapping("/echo/parts")
    public Mono<Void> echoParts(ServerWebExchange exchange, @RequestBody Flux<Part> parts) {
        Mono<MultiValueMap<String, Part>> multiValueMap = parts.collectMultimap(Part::name).map(this::toMultiValueMap);
        MultipartHttpMessageWriter multipartHttpMessageWriter = new MultipartHttpMessageWriter(Arrays.asList(
                new EncoderHttpMessageWriter<>(CharSequenceEncoder.textPlainOnly()),
                new ResourceHttpMessageWriter()
        ));

        return multipartHttpMessageWriter.write(multiValueMap, ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class), MediaType.MULTIPART_FORM_DATA,
                exchange.getResponse(), Collections.emptyMap());
    }

    @PostMapping("/echo/body")
    public Mono<Void> echoBody(ServerWebExchange exchange) {
        Flux<DataBuffer> body = exchange.getRequest().getBody();
        return exchange.getResponse().writeAndFlushWith(Flux.just(body));
    }

    /**
     * fail:
     * "error": "Internal Server Error",
     * "message": "No suitable writer found for part: uid"
     */
    @PostMapping(value = "/echo/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Void> echoMultipart(ServerWebExchange exchange,
                                    @RequestBody Flux<Part> parts,
                                    @RequestBody MultiValueMap<String, Part> multiValueMap) {

      /*  MultipartHttpMessageWriter multipartHttpMessageWriter = new MultipartHttpMessageWriter(Arrays.asList(
                new EncoderHttpMessageWriter<>(CharSequenceEncoder.textPlainOnly()),
                new ResourceHttpMessageWriter()
        ));*/

//        todo : solve  "No suitable writer found for part: uid"
        return multipartHttpMessageWriter.write(Flux.just(multiValueMap), ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class), MediaType.MULTIPART_FORM_DATA,
                exchange.getResponse(), Collections.emptyMap());
    }

    private LinkedMultiValueMap<String, Part> toMultiValueMap(Map<String, Collection<Part>> map) {
        return new LinkedMultiValueMap<>(map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> toList(e.getValue()))));
    }

    private List<Part> toList(Collection<Part> collection) {
        return collection instanceof List ? (List<Part>) collection : new ArrayList<>(collection);
    }


    @Autowired
    public void setWebClientAutoConfiguration(WebClientAutoConfiguration webClientAutoConfiguration) {
        this.webClientAutoConfiguration = webClientAutoConfiguration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        WebClient.Builder builder = this.webClientAutoConfiguration.webClientBuilder();

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.withDefaults();

        httpMessageWriters = exchangeStrategies.messageWriters();
        multipartHttpMessageWriter = new MultipartHttpMessageWriter(httpMessageWriters, new FormHttpMessageWriter());

        // TODO: 2018/6/12  obtain message writer
    }
}
