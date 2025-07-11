package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Component;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ComponentRepository extends ReactiveCrudRepository<Component,Long> {
    Mono<Component> findByNameAndEimId(String componentName, long id);

}
