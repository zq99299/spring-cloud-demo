package cn.mrcode.springcloud;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品
 * @author mrcode
 * @date 2022/5/5 22:00
 */
@Data
// 实体类需要实现 Serializable 接口，这是要求
@ToString
public class Product implements Serializable {
    private String name;
    private BigDecimal price;
}
