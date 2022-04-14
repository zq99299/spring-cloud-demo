package cn.mrcode.service;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author mrcode
 * @date 2022/4/10 22:11
 */
@Service
@Slf4j
public class SentinelAnnotationService {

    @SentinelResource(
            // 资源名
            value = "cn.mrcode.service.SentinelAnnotationService:flow",
            // 流量类型
            entryType = EntryType.OUT,
            blockHandler = "flowBlockHandler"
    )
    public String flow() {
        log.info("方法正常执行");
        return "flow";
    }

    /**
     * 触发流控的处理方法
     *
     * @param exc
     * @return
     */
    public String flowBlockHandler(BlockException exc) {
        log.info("限流");
        return "流控处理";
    }

    @SentinelResource(
            // 资源名
            value = "cn.mrcode.service.SentinelAnnotationService:fallback",
            // 流量类型
            entryType = EntryType.OUT,
            blockHandler = "fallbackBlockHandler",
            fallback = "fallbackHandler"
    )
    public String fallback(int timeout) throws InterruptedException {
        if (timeout >= 1) {
            TimeUnit.MILLISECONDS.sleep(timeout);
            log.info("方法超时后正常执行");
        } else {
            log.info("模拟业务异常");
            throw new RuntimeException("模拟业务异常");
        }

        return "fallback";
    }

    /**
     * 触发流控的处理方法
     *
     * @param exc
     * @return
     */
    public String fallbackBlockHandler(int timeout, BlockException exc) {
        log.info("限流");
        return "流控处理";
    }

    /**
     * 业务异常降级处理
     *
     * @param exc
     * @return
     */
    public String fallbackHandler(int timeout, Throwable exc) {
        log.info("降级");
        return "降级处理";
    }
}
