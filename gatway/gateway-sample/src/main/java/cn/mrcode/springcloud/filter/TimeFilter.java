package cn.mrcode.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author mrcode
 * @date 2022/3/27 14:22
 */
@Slf4j
public class TimeFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch sw = new StopWatch();
        // 定义一个计时器名称,计时开始
        sw.start(exchange.getRequest().getURI().getRawPath());

        // 这里还可以对 exchange 进行一些操作
        // 比如： exchange.getAttributes().put(xxx,nnn)

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            sw.stop();
            log.info(sw.prettyPrint());
        }));
    }
}
