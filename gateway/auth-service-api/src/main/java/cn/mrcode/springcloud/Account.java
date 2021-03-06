package cn.mrcode.springcloud;

import lombok.*;

import java.io.Serializable;

/**
 * @author mrcode
 * @date 2022/3/27 19:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account implements Serializable {
    private String username;
    private String token;
    private String refreshToken;
}
