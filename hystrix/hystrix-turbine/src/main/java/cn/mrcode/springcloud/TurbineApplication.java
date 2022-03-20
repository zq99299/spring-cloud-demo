package cn.mrcode.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author mrcode
 * @date 2022/3/19 09:50
 */
@SpringBootApplication
@EnableDiscoveryClient // 集群配置里面根据服务名称配置，而具体集群信息依赖 eureka 上注册的集群信息
@EnableHystrix
@EnableTurbine
// @EnableAutoConfiguration  // 在 @SpringBootApplication 中已经存在了
public class TurbineApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TurbineApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
