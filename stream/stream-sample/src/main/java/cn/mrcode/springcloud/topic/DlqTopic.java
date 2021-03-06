package cn.mrcode.springcloud.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义 topic
 *
 * @author mrcode
 * @date 2022/4/6 20:58
 */
public interface DlqTopic {
    String INPUT = "dlq-consumer";
    String OUTPUT = "dlq-producer";

    // 可以被订阅的通道：也就是消费消息
    @Input(INPUT)
    SubscribableChannel input();

    // 发送消息的通道
    @Output(OUTPUT) // 会使用这里的 name 作为 bean 的名称，所以不能使用和 input 相同的名称
    MessageChannel output();
}
