/*
package com.xkeshi.apigateway.filter;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

*/
/**
 * Created by ruancl@xkeshi.com on 2017/4/6.
 * 除了http转发途径  其他途径都需要自己做熔断保护 限流措施
 *//*

@Component
public class RouteFilter extends ZuulFilter {

  // 引用远程服务
  private ReferenceConfig<GenericService> reference;



  @PostConstruct
  public void init() {
    reference = new ReferenceConfig<>();
    ConsumerConfig consumerConfig = new ConsumerConfig();
    consumerConfig.setGeneric(true);
    consumerConfig.setGroup("rcl");
    RegistryConfig registryConfig = new RegistryConfig();
    registryConfig.setAddress("zookeeper://zookeeper1.dev.xkeshi.so:2181?backup=zookeeper2.dev.xkeshi.so:2181,zookeeper3.dev.xkeshi.so:2181");
    reference.setRegistry(registryConfig);
    reference.setConsumer(consumerConfig);
    ApplicationConfig applicationConfig = new ApplicationConfig("gateway");
    reference.setApplication(applicationConfig);
  }


  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 90;
  }

  @Override
  public boolean shouldFilter() {
    RequestContext ctx = RequestContext.getCurrentContext();
    Object isSuccess = ctx.get("isSuccess");
    return isSuccess == null || (Boolean) isSuccess;
  }

  @Override
  public Object run() {
    System.out.println("判断请求类型");
    reference.setInterface("com.xkeshi.task.api.TestDubboService"); // 弱类型接口名
    reference.setVersion("1.0");
    reference.setGeneric(true); // 声明为泛化接口
    GenericService genericService = reference.get(); // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
// 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
    Object result = genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{"jam"}); // 如果返回POJO将自动转成Map
    //response.getWriter().println(result);
    RequestContext ctx = RequestContext.getCurrentContext();
    ctx.setSendZuulResponse(false);
    ctx.setResponseStatusCode(200);
    ctx.setResponseBody(result.toString());
    ctx.set("isSuccess",false);
    return null;
  }
}
*/
