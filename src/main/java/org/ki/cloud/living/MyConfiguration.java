package org.ki.cloud.living;

import com.alibaba.fastjson.JSON;
import org.ki.cloud.living.common.web.Result;
import org.ki.cloud.living.common.web.excption.RegisterException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Configuration
public class MyConfiguration {
    @Bean
    @Order(-2)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ServerCodecConfigurer serverCodecConfigurer) {
        return new ErrorWebExceptionHandler() {
            @Override
            public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
                if (ex instanceof RegisterException) { // 对 MyException 进行处理

                    Result<Object> objectResult = new Result<>();
                    objectResult.setMessage(ex.getMessage());
                    objectResult.setCode(400);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(JSON.toJSONBytes(objectResult));
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                } else {
                    return Mono.empty(); // 其他异常交换给默认异常处理
                }
            }
        };
    }
}
