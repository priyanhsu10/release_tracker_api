package com.pro.release_tracker_api.controllers;

import com.pro.release_tracker_api.dtos.ui.DeploymentHistory;
import com.pro.release_tracker_api.entities.Eim;
import com.pro.release_tracker_api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker-dashboard")
public class TrackerDashBoardController {
    private final DashboardService dashboardService;;

    //search for eims
    //search for releases
    @GetMapping("search/{searchTerm}")
    private Flux<Eim> searchEims(String searchTerm) {
        // This method should interact with the EimRepository to fetch Eims based on the search term
        // For now, returning an empty Flux as a placeholder
        return Flux.empty();
    }

    //latest history of deployments for EIM
    @GetMapping("/deployment-history/{eimId}")
    public Flux<GroupedFlux<String, DeploymentHistory>> getDeployemntHistory(long eimId) {

        return dashboardService.getHistory(eimId);
    }
    @GetMapping("/latest-deployment/{eimId}")
    public Flux<GroupedFlux<String, DeploymentHistory>> getLatestDeployment(long eimId) {

        return dashboardService.getLatestDeployment(eimId);
    }

}
