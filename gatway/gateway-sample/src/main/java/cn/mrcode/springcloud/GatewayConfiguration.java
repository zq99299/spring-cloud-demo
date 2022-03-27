package cn.mrcode.springcloud;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

/**
 * @author mrcode
 * @date 2022/3/27 10:23
 */
@Configuration
public class GatewayConfiguration {
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
                                )
                                .uri("lb://FEIGN-CLIENT")
                )
                .build();
    }
}
