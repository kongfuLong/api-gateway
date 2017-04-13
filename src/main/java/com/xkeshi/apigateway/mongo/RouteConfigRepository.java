package com.xkeshi.apigateway.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ruancl@xkeshi.com on 2017/4/13.
 */
@Repository
public interface RouteConfigRepository extends MongoRepository<RouteConfig,Long> {

    RouteConfig save(RouteConfig config);
}
