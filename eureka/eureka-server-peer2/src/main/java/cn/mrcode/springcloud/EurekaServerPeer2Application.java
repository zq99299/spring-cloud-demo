package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author mrcode
 * @date 2022/2/27 23:40
 */
@SpringBootApplication
// 启用 eureka server
@EnableEurekaServer
public class EurekaServerPeer2Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerPeer2Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
