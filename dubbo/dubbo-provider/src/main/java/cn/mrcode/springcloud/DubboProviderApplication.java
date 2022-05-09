package cn.mrcode.springcloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author mrcode
 * @date 2022/5/5 22:21
 */
@SpringBootApplication
// 启用注解
@EnableDubbo
public class DubboProviderApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
