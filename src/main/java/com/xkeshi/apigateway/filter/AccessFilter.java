package com.xkeshi.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/4/6.
 */
@Component
public class AccessFilter extends ZuulFilter {

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
    System.out.println("请求发起之前");
    //过滤逻辑
    return null;
  }
}
