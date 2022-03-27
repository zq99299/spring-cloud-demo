package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author mrcode
 * @date 2022/3/27 19:33
 */
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AuthResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        Account account = Account.builder()
                .username(username)
                .build();

        // TODO 实际上还需要验证 username + password
        String token = jwtService.token(account);
        account.setToken(token);
        account.setRefreshToken(UUID.randomUUID().toString());

        // 还需要将这个 结果 信息存放到某个地方，后续才刷新的时候才能拿到对应的账户信息
        redisTemplate.opsForValue()
                .set(account.getRefreshToken(), account);
        return AuthResponse.builder()
                .account(account)
                .code(AuthResponseCode.SUCCESS)
                .build();
    }

    /**
     * 验证 token
     *
     * @param token
     * @param username
     * @return
     */
    @GetMapping("/verify")
    @ResponseBody
    AuthResponse verify(@RequestParam("token") String token,
                        @RequestParam("username") String username) {
        boolean success = jwtService.verify(token, username);
        return AuthResponse.builder()
                // todo 此处最好用 invalid token 之类的错误码
                .code(success ? AuthResponseCode.SUCCESS : AuthResponseCode.USER_NOT_FOUND)
                .build();
    }

    /**
     * 发布 token 的时候会给定一个有效期，有效期到了之后，如果不想重新登录的话，
     * 就可以使用这个方法刷新 token，返回一个新的 token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh")
    @ResponseBody
    public AuthResponse refresh(@RequestParam("refreshToken") String refreshToken) {
        Account account = (Account) redisTemplate.opsForValue().get(refreshToken);
        if (account == null) {
            return AuthResponse.builder()
                    .code(AuthResponseCode.USER_NOT_FOUND)
                    .build();
        }

        String jwt = jwtService.token(account);
        account.setToken(jwt);
        account.setRefreshToken(UUID.randomUUID().toString());

        // 删除原来失效的刷新 token
        redisTemplate.delete(refreshToken);
        // 存入新的 token 信息
        redisTemplate.opsForValue()
                .set(account.getRefreshToken(), account);
        return AuthResponse.builder()
                .account(account)
                .code(AuthResponseCode.SUCCESS)
                .build();
    }
}
