package org.lhyf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/****
 * @author YF
 * @date 2019-04-08 11:33
 * @desc RedisConfig
 *
 **/
@Configuration
public class RedisConfig {

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool("192.168.199.250",6379);
    }
}
