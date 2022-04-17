package cn.mrcode.sentinel;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

/**
 * @author mrcode
 * @date 2022/4/17 15:27
 */
@Configuration
@Data
public class JavaConfigBean {
    @Value("${timeout:0}")
    private Integer timeout;

    @Value("${newKey:'默认值'}")
    private String newKey;
}
