package cn.mrcode.springcloud.remoteevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author mrcode
 * @date 2022/3/26 14:42
 */
@Component
@Slf4j
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("Received MyCustomRemoteEvent - message: ");
    }
}
