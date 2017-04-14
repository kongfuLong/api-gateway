package com.xkeshi.apigateway.core;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.Setter;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.xkeshi.apigateway.mongo.HystrixConfig;
import com.xkeshi.apigateway.mongo.HystrixConfigRepository;
import com.xkeshi.apigateway.mongo.RouteConfig;
import com.xkeshi.apigateway.mongo.RouteConfigRepository;
import com.xkeshi.core.utils.CollectionUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@Component
public class DynamicConfigrationImpl extends HystrixPropertiesStrategy implements DynamicConfigration {

  private final Logger logger = LoggerFactory.getLogger(DynamicConfigrationImpl.class);

  @Autowired
  private ZuulProperties zuulProperties;

  @Autowired
  private RouteConfigRepository routeConfigRepository;

  @Autowired
  private HystrixConfigRepository hystrixConfigRepository;


  @PostConstruct
  private void init(){
    //载入自定义熔断配置策略
    HystrixPlugins.getInstance().registerPropertiesStrategy(this);
    //载入路由策略配置
    reloadRouteRule();
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
    HystrixConfig config = hystrixConfigRepository.findByServiceId(commandKey.name());
    //自定义hystrix配置
    if(config != null){
      builder.withExecutionIsolationSemaphoreMaxConcurrentRequests(config.getExecutionIsolationSemaphoreMaxConcurrentRequests());
      builder.withExecutionTimeoutInMilliseconds(config.getExecutionTimeoutInMilliseconds());
      logger.info("配置hystrix::"+commandKey.name());
    }

    return super.getCommandProperties(commandKey, builder);
  }

  @Override
  public void reloadHystrix() {
    HystrixPropertiesFactory.reset();
  }

  @Override
  public void reloadRouteRule() {
    //初始化路由策略配置
    Map<String,ZuulRoute> map = new HashMap<>();
    List<RouteConfig> routeConfigs = routeConfigRepository.findAll();
    if(CollectionUtils.isNotEmpty(routeConfigs)){
      routeConfigs.forEach(config->map.put(
          config.getServiceId(),
          new ZuulRoute(config.getServiceId(),
              config.getPathReg(),
              config.getServiceName(),
              config.getUrl(),
              config.getStripPrefix(),
              config.getRetryable(),
              config.getSensitiveHeaders()
          )
      ));
      zuulProperties.setRoutes(map);
    }
  }

  @Override
  public void reloadRouteRule(String serviceId) {
    RouteConfig config = routeConfigRepository.findByServiceId(serviceId);
    if(config == null){
      zuulProperties.getRoutes().remove(serviceId);
    }else {
      zuulProperties.getRoutes().put(serviceId,new ZuulRoute(config.getServiceId(),
          config.getPathReg(),
          config.getServiceName(),
          config.getUrl(),
          config.getStripPrefix(),
          config.getRetryable(),
          config.getSensitiveHeaders()));
    }
  }
}
