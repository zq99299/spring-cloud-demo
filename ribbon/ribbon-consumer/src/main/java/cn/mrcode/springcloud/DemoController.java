package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrcode
 * @date 2022/3/1 21:46
 */
@RestController
@Slf4j
public class DemoController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        // 直接使用服务名和地址
        String target = String.format("http://eureka-client/sayHi");
        log.info("url is {}", target);

        // 再配合 restTemplate 工具发起请求
        String forObject = restTemplate.getForObject(target, String.class);
        return forObject;
    }

    @PostMapping("/hello")
    public Friend helloPost() {
        String target = String.format("http://eureka-client/sayHi");
        log.info("url is {}", target);

        // 再配合 restTemplate 工具发起请求
        Friend friend = new Friend();
        friend.setName("eureka-consumer");
        return restTemplate.postForObject(target, friend, Friend.class);
    }
}
