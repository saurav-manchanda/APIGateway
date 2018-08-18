/********************************************************************************* *
 * Purpose: To create an implementation to GoogleKeep(ToDoApplication) with microservices.
 * Creating a configuration class.
 * @author Saurav Manchanda
 * @version 1.0
 * @since 30/07/2018
 *********************************************************************************/
package com.bridgelabz.zuul.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.bridgelabz.zuul.filter.AuthenticationPreFilter;

/**
 * @author Saurav
 *         <p>
 *         This class is a configuration class to get the beans required at
 *         situations
 *         </p>
 */
@Configuration
@Component
public class ZuulConfig {
	/**
	 * @return jedisConFactory
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		return jedisConFactory;
	}

	/**
	 * @return redis template
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
/**
 * Method to get the bean for AuthenticationPreFilter
 * @return
 */
	@Bean
	public AuthenticationPreFilter authenticationPreFilter() {
		return new AuthenticationPreFilter();
	}
}
