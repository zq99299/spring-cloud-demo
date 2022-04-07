package cn.mrcode.springcloud.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 延迟消息 topic
 *
 * @author mrcode
 * @date 2022/4/6 20:58
 */
public interface DelayedTopic {
    String INPUT = "delayed-consumer";
    String OUTPUT = "delayed-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
