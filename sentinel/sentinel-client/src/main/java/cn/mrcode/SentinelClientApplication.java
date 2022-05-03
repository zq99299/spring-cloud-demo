package cn.mrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * @author mrcode
 * @date 2022/4/10 19:35
 */
@SpringBootApplication
public class SentinelClientApplication {
    public static void main(String[] args) {
        /*
            启动时加入 JVM 参数 -Dcsp.sentinel.dashboard.server=consoleIp:port 指定控制台地址和端口。
            若启动多个应用，则需要通过 -Dcsp.sentinel.api.port=xxxx 指定客户端监控 API 的端口（默认是 8719）。
         */
        // 这里为了方便记录，使用手动编码方式将参数放置到系统参数中
        Properties properties = System.getProperties();
        // 控制台地址
        properties.put("csp.sentinel.dashboard.server", "127.0.0.1:9000");
        // 应用名称
        properties.put("project.name", "sentinel-client");
        SpringApplication.run(SentinelClientApplication.class, args);
    }
}
