package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrcode
 * @date 2022/4/3 20:12
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
@RestController
public class SleuthTraceBApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SleuthTraceBApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate lb() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/traceB")
    public String traceB() {
        log.info("------Trace B");
        return "Trace B";
    }
}
