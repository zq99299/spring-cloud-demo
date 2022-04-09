package cn.mrcode.springcloud.biz;

import cn.mrcode.springcloud.topic.DelayedTopic;
import cn.mrcode.springcloud.topic.ErrorTopic;
import cn.mrcode.springcloud.topic.GroupTopic;
import cn.mrcode.springcloud.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mrcode
 * @date 2022/4/5 19:38
 */
@Slf4j
// 绑定信道（类似 topic 的概念），加入初始化流程中
@EnableBinding(value = {
        Sink.class,
        MyTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ErrorTopic.class
})
public class StreamConsumer {
    // 监听信道
    @StreamListener(Sink.INPUT)
    public void consume(Object payload) {
        log.info("payload : {}", payload);
    }

    @StreamListener(MyTopic.INPUT)
    public void consumeMyMessage(Object payload) {
        log.info("payload : {}", payload);
    }

    @StreamListener(GroupTopic.INPUT)
    public void consumeGroupMessage(Object payload) {
        log.info("payload : {}", payload);
    }

    @StreamListener(DelayedTopic.INPUT)
    public void consumeDelayedMessage(MessageBean payload) {
        log.info("payload : {}", payload);
    }

    // 异常重试 - 单机版
    private AtomicInteger count = new AtomicInteger(0);

    @StreamListener(ErrorTopic.INPUT)
    public void consumeErrorMessage(MessageBean payload) {
        log.info("payload : {}", payload);
        // 重试次数等于 3 的时候，就放行
        if (count.incrementAndGet() % 3 == 0) {
            log.info("消费成功");
            count.set(0);
        } else {
            log.info("抛出一个异常");
            throw new RuntimeException("故意而为之");
        }
    }
}
