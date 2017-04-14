package com.xkeshi.apigateway.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ruancl@xkeshi.com on 2017/4/14.
 */
public interface HystrixConfigRepository extends MongoRepository<HystrixConfig,String> {

   HystrixConfig findByServiceId(String serviceId);

   void deleteByServiceId(String serviceId);
}
