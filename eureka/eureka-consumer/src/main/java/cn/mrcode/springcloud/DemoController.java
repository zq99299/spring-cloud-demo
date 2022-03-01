package cn.mrcode.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author mrcode
 * @date 2022/3/1 21:46
 */
@RestController
@Slf4j
public class DemoController {
    /**
     * eureka client 中的一个提供一个简易的负载均衡器
     */
    @Autowired
    private LoadBalancerClient client;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello")
    public String hello() {
        // 从负载均衡器中选择一个可用的服务实例
        ServiceInstance serviceInstance = client.choose("eureka-client");
        if (serviceInstance == null) {
            return "没有可用的服务实例";
        }

        // 然后根据服务实例拼接出 我们要访问的目标 controller 地址
        String target = String.format("http://%s:%s/sayHi",
                serviceInstance.getHost(),
                serviceInstance.getPort());
        log.info("url is {}", target);

        // 再配合 restTemplate 工具发起请求
        String forObject = restTemplate.getForObject(target, String.class);
        return forObject;
    }

    @PostMapping("/hello")
    public Friend helloPost() {
        // 从负载均衡器中选择一个可用的服务实例
        ServiceInstance serviceInstance = client.choose("eureka-client");
        if (serviceInstance == null) {
            return null;
        }

        // 然后根据服务实例拼接出 我们要访问的目标 controller 地址
        String target = String.format("http://%s:%s/sayHi",
                serviceInstance.getHost(),
                serviceInstance.getPort());
        log.info("url is {}", target);

        // 再配合 restTemplate 工具发起请求
        Friend friend = new Friend();
        friend.setName("eureka-consumer");
        return restTemplate.postForObject(target, friend, Friend.class);
    }
}
