package cn.mrcode.springcloud.remoteevent;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;
/**
 * @author mrcode
 * @date 2022/3/26 14:40
 */
public class MyEvent extends RemoteApplicationEvent {
    protected MyEvent() {
        super();
    }

    protected MyEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
