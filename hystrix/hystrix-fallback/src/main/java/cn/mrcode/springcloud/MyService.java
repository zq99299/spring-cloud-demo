package cn.mrcode.springcloud;

import cn.mrcode.springcloud.hystrix.Fallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author mrcode
 * @date 2022/3/19 09:52
 */
@FeignClient(value = "feign-client", fallback = Fallback.class)
public interface MyService extends IService {
}
