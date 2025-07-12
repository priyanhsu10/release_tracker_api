package com.pro.release_tracker_api.controllers;

import com.pro.release_tracker_api.dtos.ui.ComponentDto;
import com.pro.release_tracker_api.dtos.ui.DeploymentHistory;
import com.pro.release_tracker_api.entities.Eim;
import com.pro.release_tracker_api.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracker-dashboard")
public class TrackerDashBoardController {
    private final DashboardService dashboardService;;

    //search for eims
    //search for releases
    @GetMapping("/eims/search/{searchTerm}")
    private Flux<Eim> searchEims(@PathVariable String searchTerm) {
        // This method should interact with the EimRepository to fetch Eims based on the search term
        // For now, returning an empty Flux as a placeholder
        return  dashboardService.searchEim(searchTerm);
    }

    //latest history of deployments for EIM
    @GetMapping("/history/{eimId}")
    public Mono<Map<String, List<DeploymentHistory>>>  getDeployemntHistory(@PathVariable long eimId) {

        return dashboardService.getHistory(eimId);
    }
    @GetMapping("/latest-deployment/{eimId}")
    public Mono<Map<String, List<DeploymentHistory>>> getLatestDeployment(@PathVariable long eimId) {

        return dashboardService.getLatestDeployment(eimId);
    }

    @GetMapping("/components/{eimId}")
    public Flux<ComponentDto>  getComponents(@PathVariable long eimId) {

        return dashboardService.getComponent(eimId);
    }
    @GetMapping("/eims/{eimId}")
    public Mono<Eim>  getEim(@PathVariable long eimId) {

        return dashboardService.getEim(eimId);
    }
}
