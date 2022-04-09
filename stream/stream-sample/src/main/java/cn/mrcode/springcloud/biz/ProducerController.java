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

    @Autowired
    private DlqTopic dlqTopic;

    // 死信队列测试
    @PostMapping("/send-to-dlq")
    public void sendMessageToDlq(@RequestParam("body") String body) {
        MessageBean messageBean = new MessageBean();
        messageBean.setPayload(body);
        dlqTopic.output()
                .send(MessageBuilder.withPayload(messageBean).build());
    }

    @Autowired
    private FallbackTopic fallbackTopic;

    // 自定义异常处理 fallback + 升版
    @PostMapping("/send-to-fallback")
    public void sendMessageToFallback(@RequestParam("body") String body,
                                      @RequestParam(value = "version",defaultValue = "1.0") String version) {
        MessageBean messageBean = new MessageBean();
        messageBean.setPayload(body);
        // 假设调用下单接口
        // 不同的 app 可能调用版本不同，
        // 老的 app 可能还是调用的是：placeOrderV1
        // 新的 app 可能调用的是最新的 placeOrderV2
        /*
          这里有两种方法：
          1. 发送到不同的队列中
          2. 传递一个信息
         */

        fallbackTopic.output()
                .send(MessageBuilder.withPayload(messageBean)
                        // 比如这里使用 header 传递
                        .setHeader("version",version)
                        .build());
    }
}
