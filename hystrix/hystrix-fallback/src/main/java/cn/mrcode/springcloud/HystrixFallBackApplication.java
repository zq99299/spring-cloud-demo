package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mrcode
 * @date 2022/3/19 09:50
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker  // 断路器
public class HystrixFallBackApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixFallBackApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
