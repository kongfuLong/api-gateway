package com.xkeshi.netty.registry;


import com.xkeshi.mongo.Api;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public interface ApiLocator {

    Api locationApi(String apiName);

}
