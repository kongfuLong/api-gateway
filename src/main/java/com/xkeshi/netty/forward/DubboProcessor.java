package com.xkeshi.netty.forward;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSONPObject;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.xkeshi.core.utils.StringUtils;
import com.xkeshi.netty.entity.DubboParam;
import com.xkeshi.netty.entity.DubboRequest;
import com.xkeshi.netty.entity.XResponse;
import com.xkeshi.netty.exception.XGatewayException;
import com.xkeshi.netty.util.Constant;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
@Component
public class DubboProcessor implements Forward<DubboRequest>{

  // 引用远程服务
  private ReferenceConfig<GenericService> reference;


  private ApplicationConfig applicationConfig;



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
    applicationConfig = new ApplicationConfig();
  }



  @Override
  public DubboRequest transformRequest(HttpRequest request, String content) {
    DubboRequest dubboRequest = new DubboRequest();

    String api = request.headers().get(Constant.API_KEY);
    if(StringUtils.isBlank(api)){
      throw new XGatewayException("请求参数缺少api");
    }
    dubboRequest.setApi(api);
    String dubboJson = request.headers().get(Constant.INNER_PARAM_KEY);
    if(StringUtils.isBlank(dubboJson)){
      throw new XGatewayException("请求参数缺少api");
    }

    try {
      dubboRequest.setDubboParam(JSON.parseObject(dubboJson, DubboParam.class));
    }catch (Exception e){
      throw new XGatewayException("请求参数不合法");
    }

    HttpHeaders headers = request.headers();
    Map<String,String> mhead = new HashMap<>();
    if (!headers.isEmpty()) {
      for (Map.Entry<String, String> h : headers) {
        CharSequence key = h.getKey();
        CharSequence value = h.getValue();
        if(key.equals("Content-Length"))
        {
          continue;
        }
        mhead.put(key.toString(),value.toString());
      }
    }
    dubboRequest.setHeaders(mhead);
    dubboRequest.setContent(content);
    return dubboRequest;
  }

  @Override
  public XResponse forward(DubboRequest o) {
    DubboParam dubboParam = o.getDubboParam();
    String interf = dubboParam.getInterfaceName();
    String version = dubboParam.getVersion();
    String method = dubboParam.getMethod();
    String returnType = dubboParam.getReturnType();
    String param = dubboParam.getParam();
    if(StringUtils.isBlank(interf) || StringUtils.isBlank(version) || StringUtils.isBlank(method) ){
      throw new XGatewayException("请求参数不合法");
    }

    applicationConfig.setName(o.getApi());
    reference.setApplication(applicationConfig);
    reference.setInterface(interf); // 弱类型接口名
    reference.setVersion(version);
    reference.setGeneric(true); // 声明为泛化接口
    GenericService genericService = reference.get(); // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用
// 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
    Object result = genericService.$invoke(method, new String[]{returnType}, new Object[]{param}); // 如果返回POJO将自动转成Map
    System.out.println(result+":::-----------asdasdasd");
    XResponse xResponse = new XResponse();
    xResponse.setContent(result.toString());
    xResponse.setStatus(200);
    return xResponse;
  }
}
