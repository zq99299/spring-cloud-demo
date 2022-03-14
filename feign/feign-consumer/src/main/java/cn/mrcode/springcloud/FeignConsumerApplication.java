package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mrcode
 * @date 2022/2/27 23:40
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 会扫描被 @FeignClient 注解的类，然后开启 Feign 的功能
public class FeignConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
