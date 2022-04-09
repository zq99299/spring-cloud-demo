package cn.mrcode.springcloud.biz;

import cn.mrcode.springcloud.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.TimeUnit;
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
        ErrorTopic.class,
        RequeueTopic.class,
        DlqTopic.class,
        FallbackTopic.class,
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

    @StreamListener(RequeueTopic.INPUT)
    public void consumeRequeueMessage(MessageBean payload) throws InterruptedException {
        log.info("payload : {}", payload);
        // 这里重入队列由于重试次数无效了，如果你有重试次数的需求，就只能自己实现了。
        TimeUnit.SECONDS.sleep(5);
        log.info("抛出一个异常");
        throw new RuntimeException("故意而为之");
    }

    // 死信队列
    @StreamListener(DlqTopic.INPUT)
    public void consumeDlqMessage(MessageBean payload) throws InterruptedException {
        log.info("dlq payload : {}", payload);
        // 重试次数等于 3 的时候，就放行
        if (count.incrementAndGet() % 3 == 0) {
            log.info("dlq 消费成功");
            // count.set(0);
        } else {
            log.info("dlq 抛出一个异常");
            throw new RuntimeException("故意而为之");
        }
    }


    // Fallback + 升级版本
    @StreamListener(value = FallbackTopic.INPUT)
    public void consumeFallbackMessage(MessageBean payload,
                                       @Header("version") String version) {
        log.info("payload : {}", payload);

        // 这里使用判定的方式，其实在 @StreamListener 中还提供了一个 condition 参数，就可以用来判定 header 中的 version 是否等于某个值
        if ("1.0".equals(version)) {
            log.info("消费成功");
        } else if ("2.0".equals(version)) {
            log.info("版本不支持");
            throw new RuntimeException("版本不支持");
        } else {
            log.info("消费成功,version={}", version);
        }
    }

    // inputChannel 命名： {destination}.{group}.errors
    @ServiceActivator(inputChannel = "fallback-topic.fallback-group.errors")
    public void fallback(Message<?> message){
        log.info("fallback entered");
    }
}
