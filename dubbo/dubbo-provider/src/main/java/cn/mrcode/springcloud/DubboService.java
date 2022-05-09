package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author mrcode
 * @date 2022/5/5 22:23
 */
@Service // 注意这里的 @Service 不是 Spring 的，是 dubbo 的
@Slf4j
public class DubboService implements IDubboService {
    @Override
    public Product publish(Product prod) {
        log.info("商品发布逻辑：" + prod);
        return prod;
    }
}
