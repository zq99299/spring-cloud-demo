package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/2/28 22:00
 */
@RestController
@Slf4j
public class DemoController {
    @Value("${server.port}")
    private String port;

    @GetMapping("/sayHi")
    public String sayHi() {
        return "This is " + port;
    }

    @PostMapping("/sayHi")
    public Friend friend(@RequestBody Friend friend) {
        log.info("You are {}", friend.getName());
        friend.setPort(port);
        return friend;
    }
}
