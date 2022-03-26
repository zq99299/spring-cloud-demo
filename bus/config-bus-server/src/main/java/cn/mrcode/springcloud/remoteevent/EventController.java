package cn.mrcode.springcloud.remoteevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/3/26 14:44
 */
@RestController
@Slf4j
public class EventController {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ServiceMatcher serviceMatcher;

    @PostMapping("/bus/publish/myevent")
    public boolean publishMyEvent() {
        // *:** 与 服务ID: config-bus-client:61002:3ea1033c4cc97a5eba5c1765699d2bbb
        // 使用 PathMatcher 进行匹配
        MyEvent event = new MyEvent("第一个例子", serviceMatcher.getServiceId(), "*:**");
        try {
            // 可以注入 ApplicationEventPublisher 来发送 event
            // eventPublisher.publishEvent(event);
            // 也可以直接使用
            applicationContext.publishEvent(event);
            return true;
        } catch (Exception e) {
            log.error("failed in publishing event", e);
        }
        return false;
    }
}
