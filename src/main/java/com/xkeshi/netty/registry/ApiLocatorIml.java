package com.xkeshi.netty.registry;

import com.xkeshi.netty.entity.ApiUri;
import org.springframework.stereotype.Component;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
@Component
public class ApiLocatorIml implements ApiLocator {

  @Override
  public void registApi(ApiUri apiUri) {

  }

  @Override
  public ApiUri locatApi(String apiName) {
    return null;
  }
}
