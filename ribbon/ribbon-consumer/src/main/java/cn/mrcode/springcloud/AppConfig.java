package cn.mrcode.springcloud;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrcode
 * @date 2022/3/7 21:53
 */
@Configuration
public class AppConfig {
    @Bean
    // 用于标记要配置为使用 LoadBalancerClient 的 RestTemplate 或 WebClient bean 的注解
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
