package cn.mrcode.springcloud.biz;

import cn.mrcode.springcloud.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mrcode
 * @date 2022/4/6 21:02
 */
@RestController
@Slf4j
public class ProducerController {
    @Autowired
    private MyTopic myTopic;

    @PostMapping("/send")
    public void sendMessage(@RequestParam("body") String body) {
        myTopic.output().send(MessageBuilder.withPayload(body).build());
    }
}
