package com.xkeshi.apigateway.core;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.Setter;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@Component
public class DynamicConfigration extends HystrixPropertiesStrategy {
  @Autowired
  private ZuulProperties zuulProperties;

  @PostConstruct
  private void init(){
    //载入自定义熔断配置策略
    HystrixPlugins.getInstance().registerPropertiesStrategy(this);
    //初始化路由策略配置
    Map<String,ZuulRoute> map = new HashMap<>();
    map.put("client-rcl",new ZuulRoute("client-rcl","/a/*","client-rcl",null,true,null,new LinkedHashSet<>()));
    map.put("client-rcl2",new ZuulRoute("client-rcl2","/b/*","client-rcl2",null,true,null,new LinkedHashSet<>()));
    zuulProperties.setRoutes(map);

  }

  /**
   * 只会在第一次访问时候来加载策略  会在内存做缓存
   * @param commandKey
   * @param builder
   * @return
   */
  @Override
  public HystrixCommandProperties getCommandProperties(HystrixCommandKey commandKey,
      Setter builder) {
    //自定义hystrix配置
    if(HystrixCommandKey.Factory.asKey("client-rcl2").equals(commandKey)){
      builder.withExecutionIsolationSemaphoreMaxConcurrentRequests(5);
      builder.withExecutionTimeoutInMilliseconds(3000);
      System.out.println("配置.........");
    }

    return super.getCommandProperties(commandKey, builder);
  }
}
