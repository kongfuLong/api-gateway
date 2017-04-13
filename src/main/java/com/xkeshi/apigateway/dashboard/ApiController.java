package com.xkeshi.apigateway.dashboard;

import com.xkeshi.apigateway.mongo.RouteConfig;
import com.xkeshi.apigateway.mongo.RouteConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@RestController
@RequestMapping("/manager")
public class ApiController {

  @Autowired
  private RouteConfigRepository routeConfigRepository;

  @RequestMapping("/addapi")
  public String addApiRoute(@RequestParam("service_id")String serviceId,@RequestParam("path")String pathreg){
    return "ok"+routeConfigRepository.save(new RouteConfig(serviceId,pathreg)).getServiceId();
  }
}
