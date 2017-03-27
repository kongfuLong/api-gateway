package com.xkeshi.netty.repository;

import com.xkeshi.mongo.Api;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ruancl@xkeshi.com on 2017/3/27.
 */
public interface ApiRepository extends MongoRepository<Api,String> {

    Api findByName(String name);

}
