package com.xkeshi.netty;

import com.xkeshi.netty.forward.ForwardStrategy;
import com.xkeshi.netty.registry.ApiLocator;
import com.xkeshi.netty.registry.ApiLocatorIml;
import com.xkeshi.netty.repository.ApiRepository;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@ComponentScan("com.xkeshi.netty.*")
public class ApiGatewayServiceConfiguration  {


  @Bean
  public HttpClient httpClient(){
    return HttpClients.custom()
        .setConnectionManager(new PoolingHttpClientConnectionManager())
        .build();
  }

  @Bean
  public ApiLocator apiLocator(ApiRepository apiRepository){
    // TODO: 2017/3/27 如果mongodb不在此处进行一次操作,后面调用将无响应  一直未找到原因 待解决 
    System.out.println("当前api共有:"+apiRepository.findAll().size()+"个");
    return new ApiLocatorIml(apiRepository);
  }

  @Bean
  public HttpSnoopServer startServer(ForwardStrategy forwardStrategy){
    return new HttpSnoopServer(forwardStrategy);
  }

}
