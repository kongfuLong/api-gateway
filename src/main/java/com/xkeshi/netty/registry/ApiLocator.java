package com.xkeshi.netty.registry;

import com.xkeshi.netty.entity.ApiUri;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public interface ApiLocator {

    void registApi(ApiUri apiUri);

    ApiUri locatApi(String apiName);

}
