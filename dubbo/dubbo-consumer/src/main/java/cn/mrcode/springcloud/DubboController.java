package cn.mrcode.springcloud;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author mrcode
 * @date 2022/5/9 20:44
 */
@RestController
public class DubboController {
    //  @Reference 注解中有很多的属性
    @Reference(
            // 指定均衡负载类型
            loadbalance = "roundrobin"
    )
    private IDubboService iDubboService;

    @PostMapping("/publish")
    public Product publish(@RequestParam String name) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(new BigDecimal("99.6"));
        iDubboService.publish(product);
        return product;
    }
}
