package cn.mrcode.springcloud;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/3/14 22:16
 */
@RestController
@RequestMapping
public class DemoController {
    @Autowired
    private MyService myService;

    @GetMapping("/fallback")
    public String fallback() {
        return myService.error();
    }

    @GetMapping("/timeout")
    public String timeout(int timeout) {
        return myService.retry(timeout);
    }


    @Autowired
    private RequestService requestService;

    @GetMapping("/request-cache")
    public Friend requestCache(String name) {
        // 帮你调用 close 方法，也就是 try...finally  在  finally 中调用 close
        @Cleanup HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Friend friend = requestService.requestCache(name);
        // 在同一个上下文中 只被调用一次
        friend = requestService.requestCache(name);
        return friend;
    }
}
