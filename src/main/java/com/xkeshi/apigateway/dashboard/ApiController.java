package com.xkeshi.apigateway.dashboard;

import com.alibaba.dubbo.common.json.JSON;
import com.mongodb.WriteResult;
import com.xkeshi.apigateway.core.DynamicConfigration;
import com.xkeshi.apigateway.mongo.RouteConfig;
import com.xkeshi.apigateway.mongo.RouteConfigRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@RestController
@RequestMapping("/apis")
public class ApiController {

  @Autowired
  private RouteConfigRepository routeConfigRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private DynamicConfigration dynamicConfigration;

  @RequestMapping(value = "/",method = RequestMethod.PUT)
  public String addApiRoute(@RequestParam("service_id")String serviceId,@RequestParam("path")String pathreg){
    if(routeConfigRepository.save(new RouteConfig(serviceId,pathreg)) == null){
      return "插入失败";
    }else {
      dynamicConfigration.reloadRouteRule(serviceId);
      return "插入成功";
    }
  }

  @RequestMapping(value = "/{serviceId}",method = RequestMethod.PUT)
  public String updateApiRoute(@PathVariable String serviceId,@RequestParam("path")String pathreg){
      WriteResult result = mongoTemplate.updateFirst(Query.query(Criteria.where("serviceId").is(serviceId)), Update.update("pathReg",pathreg),RouteConfig.class);
      if(result.getN() == 1){
        dynamicConfigration.reloadRouteRule(serviceId);
      }
      return "affect row : "+result.getN();
  }

  @RequestMapping(value = "/{serviceId}",method = RequestMethod.DELETE)
  public String delApiRoute(@PathVariable String serviceId){
    routeConfigRepository.deleteByServiceId(serviceId);
    if(routeConfigRepository.findByServiceId(serviceId) == null){
      return "删除失败";
    }else {
      dynamicConfigration.reloadRouteRule(serviceId);
      return "删除成功";
    }
  }


  @RequestMapping(value = "/{serviceId}",method = RequestMethod.GET)
  public String findApiRoute(@PathVariable String serviceId) throws IOException {
    RouteConfig routeConfig = routeConfigRepository.findByServiceId(serviceId);
    if(routeConfig == null){
      return "找不到该api";
    }
    return JSON.json(routeConfig);
  }
  @RequestMapping(value = "/",method = RequestMethod.GET)
  public String listApiRoute() throws IOException {
      List<RouteConfig> configs = routeConfigRepository.findAll();
      return JSON.json(configs);
  }


}
