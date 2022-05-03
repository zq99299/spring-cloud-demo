package cn.mrcode.apollo;

import cn.mrcode.service.SentinelAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/5/3 10:22
 */
@RestController
@Slf4j
public class ApolloController {
    @Autowired
    private ApolloSentinelAnnotationService apolloSentinelAnnotationService;

    @GetMapping("/apollo/anno/flow")
    public String flow() {
        return apolloSentinelAnnotationService.flow();
    }
}
