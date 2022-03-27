package cn.mrcode.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mrcode
 * @date 2022/3/26 19:22
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackageClasses = AuthService.class)
public class GatewaySampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewaySampleApplication.class);
    }
}
