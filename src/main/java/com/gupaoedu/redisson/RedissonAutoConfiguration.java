package com.gupaoedu.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(Redisson.class) //条件装配
@EnableConfigurationProperties(RedissonProperties.class)
@Configuration
public class RedissonAutoConfiguration {

	@Bean
	RedissonClient redissonClient(RedissonProperties redissonProperties){
		Config config = new Config();
		String prefix = "redis://";
		if (redissonProperties.isSsl()){
			prefix = "rediss://";
		}

		SingleServerConfig singleServerConfig = config.useSingleServer()
				.setAddress(prefix + redissonProperties.getHost() + ":" + redissonProperties.getPort())
				.setConnectTimeout(redissonProperties.getTimeout());

		return Redisson.create(config);
	}

}
