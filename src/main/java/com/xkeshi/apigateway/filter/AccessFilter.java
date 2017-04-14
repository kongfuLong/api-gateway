package com.xkeshi.apigateway.filter;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.xkeshi.apigateway.core.DynamicConfigrationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/4/6.
 */
@Component
public class AccessFilter extends ZuulFilter {

  private final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    //判断此次请求是否需要过滤
    RequestContext ctx = RequestContext.getCurrentContext();
    Object isSuccess = ctx.get("isSuccess");
    return isSuccess == null || (Boolean) isSuccess;
  }

  @Override
  public Object run() {
    logger.info("请求发起之前 过滤器");

    return null;
  }
}
