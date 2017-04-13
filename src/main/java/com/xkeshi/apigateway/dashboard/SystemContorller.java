package com.xkeshi.apigateway.dashboard;

import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@RestController
@RequestMapping("/system")
public class SystemContorller {

  @RequestMapping("/hystrix/reload")
  public String reloadHystrixConfigration(){
      //hystrix 自定义配置重载
      HystrixPropertiesFactory.reset();
    return "reload hystrix configration";
  }
}
