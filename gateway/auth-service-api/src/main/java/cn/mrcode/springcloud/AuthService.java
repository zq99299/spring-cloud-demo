package cn.mrcode.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author mrcode
 * @date 2022/3/27 19:07
 */
@FeignClient("auth-service")
public interface AuthService {
    @PostMapping("/login")
    @ResponseBody
    AuthResponse login(@RequestParam("username") String username,
                       @RequestParam("password") String password);

    @GetMapping("/verify")
    @ResponseBody
    AuthResponse verify(@RequestParam("token") String token,
                        @RequestParam("username") String username);

    /**
     * 发布 token 的时候会给定一个有效期，有效期到了之后，如果不想重新登录的话，
     * 就可以使用这个方法刷新 token，返回一个新的 token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh")
    @ResponseBody
    AuthResponse refresh(@RequestParam("refreshToken") String refreshToken);
}
