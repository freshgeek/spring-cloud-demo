package top.freshgeek.springcloud.rule;

import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2020/5/2 14:08
 * @description 不能放在容器扫描类下 及子包中
 */
@Configuration
public class MyRuleConfig {

    @Bean
    public IRule rule() {
        // 默认
        RoundRobinRule roundRobinRule = new RoundRobinRule();
        RandomRule randomRule = new RandomRule();
        RetryRule retryRule = new RetryRule();
        WeightedResponseTimeRule weightedResponseTimeRule = new WeightedResponseTimeRule();
        BestAvailableRule bestAvailableRule = new BestAvailableRule();
        AvailabilityFilteringRule availabilityFilteringRule = new AvailabilityFilteringRule();
        ZoneAvoidanceRule zoneAvoidanceRule = new ZoneAvoidanceRule();
        return new AvailabilityFilteringRule();
    }

}
