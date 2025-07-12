package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Server;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Repository
public interface ServerRepository extends ReactiveCrudRepository<Server,Long> {
    Mono<List<Server>> findByEnvironmentId(long environmentId);
}
