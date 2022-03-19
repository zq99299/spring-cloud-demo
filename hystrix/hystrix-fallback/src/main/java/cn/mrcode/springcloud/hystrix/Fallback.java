package cn.mrcode.springcloud.hystrix;

import cn.mrcode.springcloud.Friend;
import cn.mrcode.springcloud.MyService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 实现降级逻辑
 *
 * @author mrcode
 * @date 2022/3/19 09:53
 */
@Slf4j
@Component
public class Fallback implements MyService {

    // 针对刚刚定义的哪一个处理
//    @Override
//    public String error() {
//        log.info("Fallback：我不想当一个害群之马");
//        // 静默处理
//        return "Fallback：我不想当一个害群之马";
//    }

    @Override
    // 看到这个 @HystrixCommand 注解
    // 其实就明白了，将这个方法也当成了一个 hystrix 的保护对象了
    // 将它的降级方法设置为 error2
    @HystrixCommand(fallbackMethod = "error2")
    public String error() {
        log.info("Fallback：第一次降级");
        throw new RuntimeException("Fallback：第一次降级");
    }

    @HystrixCommand(fallbackMethod = "error3")
    public String error2() {
        log.info("Fallback：第二次降级");
        throw new RuntimeException("Fallback：第二次降级");
    }

    public String error3() {
        log.info("Fallback：第三次次降级");
        return "success";
    }

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public Friend friend(Friend friend) {
        return null;
    }

    @Override
    public String retry(int timeout) {
        return "你迟到了！";
    }
}
