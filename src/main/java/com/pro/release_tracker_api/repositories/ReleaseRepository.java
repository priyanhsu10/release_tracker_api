package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Release;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends ReactiveCrudRepository<Release,Long> {
}
