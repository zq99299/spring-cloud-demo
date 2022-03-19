package cn.mrcode.springcloud.hystrix;

import cn.mrcode.springcloud.Friend;
import cn.mrcode.springcloud.MyService;
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
    @Override
    public String error() {
        log.info("Fallback：我不想当一个害群之马");
        // 静默处理
        return "Fallback：我不想当一个害群之马";
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
