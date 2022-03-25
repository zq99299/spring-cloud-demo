package cn.mrcode.springcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动态刷新参数
 *
 * @author mrcode
 * @date 2022/3/23 21:30
 */
@RestController
@RequestMapping("/refres")
@RefreshScope
public class RefresController {
    @Value("${words}")
    private String words;

    @GetMapping("/words")
    public String getWords() {
        System.out.println(this);
        return words;
    }
}
