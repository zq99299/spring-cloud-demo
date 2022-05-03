package cn.mrcode.apollo;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author mrcode
 * @date 2022/5/3 08:55
 */
public class ApolloDataSourceListener implements InitializingBean {
    private static final String FLOW_RULE_TYPE = "flow";
    private static final String DEGRADE_RULE_TYPE = "degrade";
    // *-flow-rules
    private static final String FLOW_DATA_ID_POSTFIX = "-" + FLOW_RULE_TYPE + "-rules";
    // *-degrade-rules
    private static final String DEGRADE_DATA_ID_POSTFIX = "-" + DEGRADE_RULE_TYPE + "-rules";

    private String applicationName;

    public ApolloDataSourceListener(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFlowRules();
    }

    /**
     * 初始化规则监听器：这里并不是指初始化的时候读取一次，
     */
    private void initFlowRules() {
        // sentinel-client-flow-rules
        String flowRuleKey = applicationName + FLOW_DATA_ID_POSTFIX;

        // 与 spring 结合的时候，需要在在 application.yaml 中定义，前面在客户端如何接入 apollo 章节已经讲解过了
//        String appId = "sentinel-demo";
//        String apolloMetaServerAddress = "http://localhost:8080";
//        System.setProperty("app.id", appId);
//        System.setProperty("apollo.meta", apolloMetaServerAddress);

        String namespaceName = "application";
        // 最好提供一个有意义的默认值
        String defaultFlowRules = "[]";

        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(
                namespaceName,
                flowRuleKey,
                defaultFlowRules,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        // ApolloDataSource 是 sentinel 提供的，里面包含了对 apollo 的监听
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }
}
