package cn.mrcode.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mrcode
 * @date 2022/4/10 10:37
 */
public class DemoTest {
    public static void main(String[] args) {
        initFlowRules(20);
        while (true) {
            Entry entry = null;
            try {
                /*
                 我们把需要控制流量的代码用
                    Sentinel API SphU.entry("HelloWorld")
                    和
                    entry.exit() 包围起来即可。
                    在下面的例子中，我们将 System.out.println("hello world"); 这端代码作为资源，用 API 包围起来（埋点）
                 */
                entry = SphU.entry("HelloWorld");
                /*您的业务逻辑 - 开始*/
                System.out.println("hello world");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                /*流控逻辑处理 - 开始*/
                System.out.println("block!");
                /*流控逻辑处理 - 结束*/
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    /**
     * 定义规则：通过规则来指定允许该资源通过的请求次数
     *
     * @param qps HelloWorld 这个资源每秒通过的请求次数
     */
    private static void initFlowRules(int qps) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置 每秒的 qps
        rule.setCount(qps);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}
