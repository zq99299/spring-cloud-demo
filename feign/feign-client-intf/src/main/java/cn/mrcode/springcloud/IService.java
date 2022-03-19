package cn.mrcode.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mrcode
 * @date 2022/2/28 22:00
 */
@FeignClient(value = "feign-client")
public interface IService {
    @GetMapping("/sayHi")
    String sayHi();

    @PostMapping("/sayHi")
    Friend friend(@RequestBody Friend friend);

    /**
     * 超时方法
     *
     * @param timeout 超时多少秒，才响应业务
     * @return
     */
    @GetMapping("/retry")
    String retry(@RequestParam("timeout") int timeout);

    /**
     * 专门抛出异常，测试 hystrix
     *
     * @return
     */
    @GetMapping("/error")
    String error();
}
