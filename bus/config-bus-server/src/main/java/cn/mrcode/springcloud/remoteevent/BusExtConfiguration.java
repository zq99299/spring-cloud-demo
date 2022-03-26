package cn.mrcode.springcloud.remoteevent;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrcode
 * @date 2022/3/26 14:41
 */
@Configuration
@RemoteApplicationEventScan(basePackageClasses = MyEvent.class)
public class BusExtConfiguration {
}
