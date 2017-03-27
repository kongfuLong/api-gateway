package com.xkeshi.dashboard.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xkeshi.dashboard.repository.ApiRepository;
import com.xkeshi.mongo.Api;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruancl@xkeshi.com on 2017/3/27.
 */
@RestController
public class ApiController {
  @Autowired
  private ApiRepository apiRepository;


  @RequestMapping(value = "/apis",method = RequestMethod.POST)
  @ResponseBody
  public String add(@RequestParam(name = "name") String name,@RequestParam(name = "host") String host,@RequestParam(name = "port") int port)  {
    Api api = new Api(name,host,port);
    apiRepository.save(api);
    return "添加成功";
  }

  @RequestMapping(value = "/apis",method = RequestMethod.DELETE)
  @ResponseBody
  public String del(@RequestParam(name = "name") String name)  {
    //Api api = apiRepository.findByName(name);
    apiRepository.deleteByName(name);
    return "删除成功";
  }

  @RequestMapping(value = "/apis",method = RequestMethod.GET)
  @ResponseBody
  public String apis(@RequestParam(name = "name",required = false) String name)  {
    try {

      if(name == null){
        return JSON.json(apiRepository.findAll());

      }else{
        return JSON.json(apiRepository.findByName(name));
      }
    } catch (IOException e) {
      //
      return "解析失败";
    }
  }

}
