package cn.mrcode.springcloud.biz;

import cn.mrcode.springcloud.topic.GroupTopic;
import cn.mrcode.springcloud.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author mrcode
 * @date 2022/4/5 19:38
 */
@Slf4j
// 绑定信道（类似 topic 的概念），加入初始化流程中
@EnableBinding(value = {
        Sink.class,
        MyTopic.class,
        GroupTopic.class
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
}
