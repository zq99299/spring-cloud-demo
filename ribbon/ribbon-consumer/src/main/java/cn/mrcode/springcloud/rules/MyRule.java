package cn.mrcode.springcloud.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 哈希一致性
 *
 * @author mrcode
 * @date 2022/3/12 12:27
 */
public class MyRule extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        // 需要注意的是：这个 key 并不是指定的 服务名称

        // 因为每次的请求调用都会选择一次服务节点，所以这里为每一个请求定制策略

        // 获取请求的 request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 主域名之后的路径
        // 这里使用简单的策略：从请求的 uri 来作为特征量
        // 在实际应用中：根据你的逻辑去选择特征量，如 userId
        String uri = request.getServletPath() + "?" + request.getQueryString();

        // 获取 uri 的 hashCode， 和 当前服务的所有实例列表
        return route(uri.hashCode(), getLoadBalancer().getAllServers());
    }

    /**
     * @param hashId     特征量的 hashCode
     * @param allServers
     * @return
     */
    private Server route(int hashId, List<Server> allServers) {
        if (CollectionUtils.isEmpty(allServers)) {
            return null;
        }
        TreeMap<Long, Server> address = new TreeMap<>();
        allServers.stream().forEach(e -> {
            // 将每一个服务节点，都虚拟出 8 个节点分散到 圆环中
            // 这里简单的做了 哈希一致性的概念，但是这个 分散 操作不是均匀分散的
            for (int i = 0; i < 8; i++) {
                long hash = hash(e.getId() + i);
                address.put(hash, e);
            }
        });
        // 这个 taiMap 很有意思，返回比 key 大的所有节点
        long hash = hash(hashId + "");
        SortedMap<Long, Server> last = address.tailMap(hash);

        // 如果没有找到，则返回第一个服务节点
        // 为空的时候说明：它在环上没有找大一个比它大的节点
        if (last.isEmpty()) {
            // 所以返回第一个节点：首尾相连，则构成一个圆环
            return address.firstEntry().getValue();
        }
        // 如果有，则返回离特征点最近的一个点
        return last.get(last.firstKey());
    }

    public long hash(String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        // 将 key 放到 md5 中
        md5.update(bytes);
        // 然后获取：摘要，16 位数
        byte[] digest = md5.digest();

        // 下面的位操作，我已经忘得差不多了
        // 计算 hashCode
        long hashCode =
                ((long) (digest[2] & 0xFF << 16))
                        | ((long) (digest[1] & 0xFF << 8))
                        | ((long) (digest[0] & 0xFF));
        return hashCode & 0xffffffff;
    }
}
