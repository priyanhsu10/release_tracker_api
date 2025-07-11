package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.Release;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReleaseRepository extends ReactiveCrudRepository<Release,Long> {
}
