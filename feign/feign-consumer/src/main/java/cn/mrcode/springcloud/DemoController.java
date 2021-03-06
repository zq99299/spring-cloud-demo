package cn.mrcode.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/3/14 22:16
 */
@RestController
@RequestMapping("/test")
public class DemoController {
    @Autowired
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return iService.sayHi();
    }
}
