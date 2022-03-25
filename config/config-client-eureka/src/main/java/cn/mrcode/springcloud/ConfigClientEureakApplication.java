package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mrcode
 * @date 2022/3/23 20:42
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientEureakApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientEureakApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
