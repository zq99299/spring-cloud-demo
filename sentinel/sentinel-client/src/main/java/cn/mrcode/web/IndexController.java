package cn.mrcode.web;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author mrcode
 * @date 2022/4/10 19:58
 */
@RestController
@Slf4j
public class IndexController {
    @PostConstruct
    public void init() {
        initFlowRules(20);
        initDegradeRules();
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

    @GetMapping("/flow")
    public String flow() throws InterruptedException {
        while (true) {
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
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
     * 定义降级规则
     */
    private static void initDegradeRules() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("cn.mrcode.web.IndexController:degrade");
        // 过去 60 秒内按 biz 异常计数降级
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 这里 60 秒内抛出 2 个异常则降级
        rule.setCount(2);
         /*
            当通过 {@link RuleConstantDEGRADE_GRADE_EXCEPTION_COUNT} 降级时，
            小于 60 秒的时间窗口将无法按预期工作。因为异常计数是按分钟求和的，
            所以当经过很短的时间窗口时，仍然可以满足降级条件
            对于这里设置的时间窗口，没有搞明白过，如果不设置，则这里配置的规则不会生效
         */
         rule.setTimeWindow(60);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    int count = 0;

    /**
     * 降级
     *
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/degrade")
    public String degrade() throws InterruptedException {
        count++;
        Entry entry = null;
        try {
            // 实际工作中的资源命名一般是：「包名.类名:资源名称」
            // 如果这个类中还有更细的资源，「包名.类名:方法名称:资源1」，使用这种多级的名称来表示
            entry = SphU.entry("cn.mrcode.web.IndexController:degrade");

            // 这里模拟异常
            if (count % 2 == 0) {
                throw new RuntimeException("模拟异常");
            } else {
                // 休眠 20 毫秒
                TimeUnit.MILLISECONDS.sleep(20);
                log.info("hello world");
                return "ok";
            }
        } catch (BlockException e) {
            log.error("被降级了");
        } catch (Throwable t) {
            Tracer.trace(t);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return "fail";
    }
}
