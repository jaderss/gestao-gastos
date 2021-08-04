package br.com.wipro.challenge.gestaogastos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class RedisConfiguration {

    private RedisServer redisServer;

    @Value("${spring.redis.port}")
    private int redisPort;

    @PostConstruct
    private void startServer() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy
    private void stopServer() {
        redisServer.stop();
    }
}
