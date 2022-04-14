package cn.mrcode.web;

import cn.mrcode.service.SentinelAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/4/10 22:12
 */
@RestController
public class SentinelAnnotationController {
    @Autowired
    private SentinelAnnotationService sentinelAnnotationService;

    @GetMapping("/anno/flow")
    public String flow() {
        return sentinelAnnotationService.flow();
    }

    @GetMapping("/anno/fallback")
    public String fallback(int timeout) throws InterruptedException {
        return sentinelAnnotationService.fallback(timeout);
    }
}
