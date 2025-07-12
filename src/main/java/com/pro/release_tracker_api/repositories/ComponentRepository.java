package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Component;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ComponentRepository extends ReactiveCrudRepository<Component,Long> {
    Mono<Component> findByNameAndEimId(String componentName, long id);

    Flux<Component> findByEimId(long eimId);
}
