package top.freshgeek.springcloud.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
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
        return new RandomRule();
    }

}
