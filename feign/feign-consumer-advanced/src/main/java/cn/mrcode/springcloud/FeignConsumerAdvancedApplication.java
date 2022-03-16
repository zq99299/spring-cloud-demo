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
@EnableFeignClients // 如果引用的 feignClient 接口所在包不是和你当前的启动类所在包一致，需要自己在这里指定包路径
public class FeignConsumerAdvancedApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignConsumerAdvancedApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
