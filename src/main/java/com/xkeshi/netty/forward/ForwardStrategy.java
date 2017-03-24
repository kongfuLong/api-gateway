package com.xkeshi.netty.forward;

import com.xkeshi.netty.exception.XGatewayException;
import com.xkeshi.netty.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/3/24.
 */
@Component
public class ForwardStrategy {

  @Autowired
  private HttpProcessor httpProcessor;

  @Autowired
  private DubboProcessor dubboProcessor;

  public Forward chooseForwardProcessor(String requestType){
    if(requestType== null || requestType.equals(Constant.HTTP)) {
      return httpProcessor;
    }else if(requestType.equals(Constant.DUBBO)){
      return dubboProcessor;
    }else {
      throw new XGatewayException("未知请求类型");
    }
  }
}
