package cn.mrcode.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 声明 Feign 客户端
 *
 * @author mrcode
 * @date 2022/3/14 22:14
 */
@FeignClient("eureka-client")  // 声明这个接口中的方法要发到哪一个服务中去
public interface IService {

    /**
     * 定义要调用的目标路径
     *
     * @return
     */
    @GetMapping("/sayHi")
    String sayHi();
}
