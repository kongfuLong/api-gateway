package com.xkeshi.apigateway.main;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ruancl@xkeshi.com on 2017/4/6.
 */
@EnableZuulProxy
@SpringCloudApplication
@ComponentScan("com.xkeshi.apigateway")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class,args);
  }

}
