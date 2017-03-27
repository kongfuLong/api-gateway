package com.xkeshi.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ruancl@xkeshi.com on 2017/3/27.
 */
@SpringBootApplication
@ComponentScan("com.xkeshi.dashboard.*")
public class Application implements EmbeddedServletContainerCustomizer{

  public static void main(String[] args) {
    SpringApplication.run(Application.class,args);
  }


  @Override
  public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
    configurableEmbeddedServletContainer.setPort(9090);
  }
}
