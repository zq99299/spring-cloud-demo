package cn.mrcode.springcloud;

import lombok.*;

/**
 * @author mrcode
 * @date 2022/3/27 19:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponse {
    private Account account;
    private Long code;
}
