package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Environment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface EnvironmentRepository extends ReactiveCrudRepository<Environment,Long> {
    Mono<Environment> findByNameAndComponentId(String environmentName, long id);
}
