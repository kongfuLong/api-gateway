package com.xkeshi.apigateway.dashboard;

import com.xkeshi.apigateway.core.DynamicConfigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@RestController
@RequestMapping("/system")
public class SystemContorller {

  @Autowired
  private DynamicConfigration dynamicConfigration;

  @RequestMapping(value = "/reload/hystrix",method = RequestMethod.GET)
  public String reloadHystrixConfigration(){
    dynamicConfigration.reloadHystrix();
    return "reload all hystrix configration";
  }

  @RequestMapping(value = "/reload/route",method = RequestMethod.GET)
  public String reloadRouteConfigration(){
    dynamicConfigration.reloadRouteRule();
    return "reload all route configration";
  }

  @RequestMapping(value = "/reload/route/{serviceId}",method = RequestMethod.GET)
  public String reloadRouteConfigration(@PathVariable String serviceId){
    dynamicConfigration.reloadRouteRule(serviceId);
    return "reload route configration :"+serviceId;
  }
}
