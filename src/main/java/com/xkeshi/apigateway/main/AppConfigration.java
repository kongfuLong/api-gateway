package com.xkeshi.apigateway.main;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by ruancl@xkeshi.com on 2017/4/14.
 */
@EnableZuulProxy
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableEurekaServer
@EnableMongoRepositories("com.xkeshi.apigateway.mongo")
@ComponentScan("com.xkeshi.apigateway")
@Configuration
public class AppConfigration {

}
