package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Environment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EnvironmentRepository extends ReactiveCrudRepository<Environment,Long> {
    Mono<Environment> findByNameAndComponentId(String environmentName, long id);
}
