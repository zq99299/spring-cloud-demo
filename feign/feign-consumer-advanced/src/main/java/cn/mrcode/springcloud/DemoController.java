package cn.mrcode.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/3/14 22:16
 */
@RestController
@RequestMapping
public class DemoController {
    @Autowired
    private IService iService;

    @GetMapping("/hello")
    public String sayHiGet() {
        return iService.sayHi();
    }

    @PostMapping("/hello")
    public Friend sayHiPost() {
        Friend friend = new Friend();
        friend.setName("feign-consumer-advanced");
        return iService.friend(friend);
    }
}
