package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.*;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface EimRepository extends ReactiveCrudRepository<Eim,Long> {
    Mono<Object> findByName(String name);

    Mono<Eim> findByEimNumber(Long eimId);
}

