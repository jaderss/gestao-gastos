package dev.jaderss.expenses.config;

import java.time.Clock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExpensesProperties.class)
class ClockConfiguration {

    @Bean
    Clock clock(ExpensesProperties properties) {
        return Clock.system(properties.timeZone());
    }
}
