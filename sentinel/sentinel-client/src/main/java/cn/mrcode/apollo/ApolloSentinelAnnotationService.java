package cn.mrcode.apollo;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author mrcode
 * @date 2022/5/3 10:21
 */
@Service
@Slf4j
public class ApolloSentinelAnnotationService {
    @SentinelResource(
            // 资源名
            value = "cn.mrcode.apollo.SentinelAnnotationService:flow",
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
        return "流控";
    }
}
