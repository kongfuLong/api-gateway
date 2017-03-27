package com.xkeshi.netty.forward;

import com.xkeshi.netty.entity.XResponse;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Created by ruancl@xkeshi.com on 2017/3/23.
 */
public interface Forward<T> {

   T transformRequest(HttpRequest httpRequest,String content);

   XResponse forward(T o);

}
