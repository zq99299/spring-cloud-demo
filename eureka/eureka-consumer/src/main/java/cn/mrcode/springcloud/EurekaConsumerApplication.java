package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mrcode
 * @date 2022/2/27 23:40
 */
@SpringBootApplication
// 启用 eureka client
//@EnableEurekaClient
@EnableDiscoveryClient  // 这个注解是通用的服务发现注解
public class EurekaConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
