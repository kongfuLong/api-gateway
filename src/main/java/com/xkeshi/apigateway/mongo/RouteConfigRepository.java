package com.xkeshi.apigateway.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
public interface RouteConfigRepository extends MongoRepository<RouteConfig,Long> {

   void deleteByServiceId(String serviceId);

   RouteConfig findByServiceId(String serviceId);

}
