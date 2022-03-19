package cn.mrcode.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mrcode
 * @date 2022/3/19 13:52
 */
@Service
@Slf4j
public class RequestService {
    @Autowired
    private MyService myService;

    @CacheResult
    // 大部分方法不需要进行降级配置，根据 @HystrixCommand 进行单独配置
    @HystrixCommand(
            // 简化配置超时配置的名称，之前针对方法签名进行配置的，这里就相当于给方法签名搞个别名？
            // hystrix.comman.MyService#retry(int).execution.isolation.thread.interruptOnTimeout
            // hystrix.comman.cacheKey.execution.isolation.thread.interruptOnTimeout
            commandKey = "cacheKey"
    )
    public Friend requestCache(@CacheKey String name) {
        log.info("进入方法：" + name);
        Friend friend = new Friend();
        friend.setName(name);
        friend = myService.friend(friend);
        log.info("执行调用后：" + name);
        return friend;
    }
}
