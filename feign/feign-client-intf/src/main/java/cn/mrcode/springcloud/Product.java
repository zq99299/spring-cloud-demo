package cn.mrcode.springcloud;

import lombok.Builder;
import lombok.Data;

/**
 * @author mrcode
 * @date 2022/3/27 13:28
 */
@Data
@Builder
public class Product {
    private Long productId;
    private String description;
    /**
     * 当前库存
     */
    private Long stock;
}
