package com.pro.release_tracker_api.repositories;

import com.pro.release_tracker_api.entities.ServerDeployment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ServerDeploymentRepository extends ReactiveCrudRepository<ServerDeployment,Long> {
}
