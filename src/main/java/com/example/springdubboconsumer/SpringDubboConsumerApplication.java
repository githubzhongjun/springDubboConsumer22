package com.example.springdubboconsumer;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

//@ComponentScan("com.example")
@SpringBootApplication
public class SpringDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDubboConsumerApplication.class, args);

        //归根结底其实就是把各个限制规则加入到FlowRuleManager管理器中
        //这里是通过代码把一个个new 出来的FlowRule添加进去而已
        //实际上我们可以通过可视化见面设置添加，比较方便
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("say");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(2);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
