package cn.mrcode.springcloud;

/**
 * @author mrcode
 * @date 2022/5/5 22:05
 */
public interface IDubboService {
    /**
     * 商品发布
     *
     * @param prod
     * @return
     */
    Product publish(Product prod);
}
