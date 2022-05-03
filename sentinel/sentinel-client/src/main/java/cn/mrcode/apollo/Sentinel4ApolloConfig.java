package cn.mrcode.apollo;

import cn.mrcode.apollo.ApolloDataSourceListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrcode
 * @date 2022/5/3 08:53
 */
@Configuration
public class Sentinel4ApolloConfig {
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public ApolloDataSourceListener apolloDataSourceListener(){
        return new ApolloDataSourceListener(applicationName);
    }
}
