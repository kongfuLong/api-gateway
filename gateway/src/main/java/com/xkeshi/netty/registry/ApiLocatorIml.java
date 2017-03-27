package com.xkeshi.netty.registry;

import com.xkeshi.mongo.Api;
import com.xkeshi.netty.repository.ApiRepository;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public class ApiLocatorIml implements ApiLocator {

  private ApiRepository apiRepository;

  public ApiLocatorIml(ApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  @Override
  public Api locationApi(String apiName) {


    return apiRepository.findByName(apiName);
  }
}
