package cn.mrcode.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/4/17 15:29
 */
@RestController
public class IndexController {
    @Autowired
    private JavaConfigBean javaConfigBean;

    @GetMapping("/index")
    public String index() {
        return javaConfigBean.getNewKey() + " , " + javaConfigBean.getTimeout();
    }
}
