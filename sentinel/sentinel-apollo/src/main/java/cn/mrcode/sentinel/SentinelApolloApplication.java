package cn.mrcode.sentinel;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mrcode
 * @date 2022/4/10 19:35
 */
@SpringBootApplication
@EnableApolloConfig
public class SentinelApolloApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelApolloApplication.class, args);
    }
}
