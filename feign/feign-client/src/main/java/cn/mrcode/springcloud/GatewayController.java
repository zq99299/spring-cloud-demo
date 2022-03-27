package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简易的秒杀业务
 *
 * @author mrcode
 * @date 2022/3/27 13:27
 */
@RestController
@Slf4j
@RequestMapping("gateway")
public class GatewayController {
    public static final Map<Long, Product> items = new ConcurrentHashMap<>();

    /**
     * 获取商品详情
     *
     * @param pid
     * @return
     */
    @GetMapping("details")
    public Product get(@RequestParam("pid") Long pid) {
        // 不包含商品信息的话，就模拟发布一个
        if (!items.containsKey("pid")) {
            Product prod = Product.builder()
                    .productId(pid)
                    .description("好吃不贵")
                    .stock(100L)
                    .build();
            // 如果已经存在则不执行添加操作
            items.putIfAbsent(pid, prod);
        }
        return items.get(pid);
    }

    /**
     * 下单接口
     *
     * @param pid
     * @return
     */
    @PostMapping("placeOrder")
    public String buy(@RequestParam("pid") Long pid) {
        Product prod = items.get(pid);
        if (prod == null) {
            return "商品不存在";
        }

        Long stock = prod.getStock();
        if (stock <= 0L) {
            return "库存不足";
        }

        synchronized (prod) {
            if (stock <= 0L) {
                return "库存不足";
            }
            // 每次只能购买一个
            prod.setStock(prod.getStock() - 1L);
        }
        return "已下单";
    }
}
