package com.xkeshi.apigateway.core;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
public interface DynamicConfigration {

  void reloadHystrix();

  void reloadRouteRule();

  void reloadRouteRule(String serviceId);
}
