package cn.mrcode.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
