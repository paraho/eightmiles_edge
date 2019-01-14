package com.paige.service.apigateway.repository;

import com.paige.service.apigateway.model.UserSessionRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRedisHashRepository extends CrudRepository<UserSessionRedis, Object> {
}
