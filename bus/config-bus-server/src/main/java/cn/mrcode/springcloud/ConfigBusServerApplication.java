package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author mrcode
 * @date 2022/3/23 20:42
 */
@SpringBootApplication
@EnableConfigServer  // 启用配置中心
@EnableDiscoveryClient
public class ConfigBusServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
