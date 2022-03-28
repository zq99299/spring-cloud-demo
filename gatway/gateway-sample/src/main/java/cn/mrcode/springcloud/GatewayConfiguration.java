package cn.mrcode.springcloud;

import cn.mrcode.springcloud.filter.AuthFilter;
import cn.mrcode.springcloud.filter.TimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;

/**
 * @author mrcode
 * @date 2022/3/27 10:23
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public TimeFilter timeFilter() {
        return new TimeFilter();
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    /**
     * 创建限流的 key
     * @return
     */
    @Bean
    public KeyResolver remoteAddrKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 定义路由定位器
     *
     * @param builder
     * @return
     */
    @Bean
    @Order
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()

                .route("feignClientWithJava",  // 为了好区分是哪里配置的，这里增加 路由 ID
                        // predicateSpec 断言
                        p -> p.path("/java/**")
                                // 一个 and 表示要配置下一个 断言 功能了
                                .and()
                                // 必须要是一个 GET 请求
                                .method(HttpMethod.GET)

                                .and()
                                // 请求头 header 中必须包含 name key
                                .header("name")
                                // 过滤器
                                .filters(f -> f.stripPrefix(1)
                                                // 给响应头添加一个 header
                                                .addResponseHeader("java-param", "gateway-config")
                                                //.filter(this.timeFilter())  // 添加自定义的 filter
//                                        .filter(this.authFilter())
                                                .filter(this.errorFilter())
                                )
                                .uri("lb://FEIGN-CLIENT")
                )
                .route(p -> p.path("/seckill/**")
                        .and().after(ZonedDateTime.now().plusMinutes(1))  // 一分钟后才能访问该路由地址
                        // .and().before()  // 在 n 时间之前有效
                        // .and().between() // 在这个时间范围内才有效
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://FEIGN-CLIENT")
                )
                .route(p -> p.path("/xx/**")
                        .filters(f -> f.redirect("302", "http://www.baidu.com"))
                        .uri("lb://FEIGN-CLIENT")
                )
                .build();
    }
}
