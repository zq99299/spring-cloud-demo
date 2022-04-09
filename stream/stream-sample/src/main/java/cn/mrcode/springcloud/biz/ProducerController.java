package cn.mrcode.springcloud.biz;

import cn.mrcode.springcloud.topic.*;
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

    @Autowired
    private GroupTopic groupTopic;

    @PostMapping("/send-to-group")
    public void sendMessageToGroup(@RequestParam("body") String body) {
        groupTopic.output().send(MessageBuilder.withPayload(body).build());
    }

    @Autowired
    private DelayedTopic delayedTopic;

    /**
     * 延迟消息
     *
     * @param body    消息内容
     * @param seconds 延迟的秒数，正常大于 0 的秒数
     */
    @PostMapping("/send-to-delayed")
    public void sendMessageToDelayed(
            @RequestParam("body") String body,
            @RequestParam("seconds") Integer seconds
    ) {
        MessageBean messageBean = new MessageBean();
        messageBean.setPayload(body);
        log.info("投递延迟消息，主要是为了看接收消息的时间，和被消费的时间");
        delayedTopic.output()
                .send(MessageBuilder.withPayload(messageBean)
                        .setHeader("x-delay", 1000 * seconds)
                        .build());
    }

    @Autowired
    private ErrorTopic errorTopic;

    // 异常重试（单机版）
    @PostMapping("/send-to-error")
    public void sendMessageToError(
            @RequestParam("body") String body) {
        MessageBean messageBean = new MessageBean();
        messageBean.setPayload(body);
        errorTopic.output()
                .send(MessageBuilder.withPayload(messageBean).build());
    }

    @Autowired
    private RequeueTopic requeueTopic;

    // 异常重试（重回队列）
    @PostMapping("/send-to-requeue")
    public void sendMessageTorequeue(
            @RequestParam("body") String body) {
        MessageBean messageBean = new MessageBean();
        messageBean.setPayload(body);
        requeueTopic.output()
                .send(MessageBuilder.withPayload(messageBean).build());
    }
}
