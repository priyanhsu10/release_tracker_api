package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EimRepository extends ReactiveCrudRepository<Eim,Long> {
    Mono<Object> findByName(String name);
}

