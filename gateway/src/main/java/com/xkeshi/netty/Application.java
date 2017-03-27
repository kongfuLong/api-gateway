package com.xkeshi.netty;

import com.xkeshi.netty.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
public class Application{

  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayServiceConfiguration.class, args);
  }
  @Autowired
  private ApiRepository apiRepository;

}
