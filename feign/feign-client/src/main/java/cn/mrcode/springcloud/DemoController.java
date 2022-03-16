package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/3/1 21:46
 */
@RestController
@Slf4j
public class DemoController implements IService {
    @Value("${server.port}")
    private String port;

    @Override
    public String sayHi() {
        return "This is " + port;
    }

    @Override
    public Friend friend(Friend friend) {
        log.info("You are {}", friend.getName());
        friend.setPort(port);
        return friend;
    }
}
