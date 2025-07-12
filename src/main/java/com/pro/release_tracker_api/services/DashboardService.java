package com.pro.release_tracker_api.services;

import com.pro.release_tracker_api.dtos.ui.ComponentDto;
import com.pro.release_tracker_api.dtos.ui.DeploymentHistory;
import com.pro.release_tracker_api.entities.Eim;
import com.pro.release_tracker_api.repositories.ComponentRepository;
import com.pro.release_tracker_api.repositories.EimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DatabaseClient databaseClient;
    private final ComponentRepository componentRepository;
    private final EimRepository eimRepository;

    public Flux<Eim> searchEim(String name) {
        String searchTerm = "%" + name + "%";
        var sql = "SELECT * FROM tbl_eims WHERE name like :name";
        return databaseClient.sql(sql)
                .bind("name", "%" + name + "%")
                .map(x -> {
                    var data = new Eim();
                    data.setId(x.get("id", Long.class));
                    data.setEimNumber(x.get("eim_number", Long.class));
                    data.setName(x.get("name", String.class));
                    data.setDescription(x.get("description", String.class));
                    return data;
                })
                .all();
    }

    public Mono<Map<String, List<DeploymentHistory>>> getLatestDeployment(long eimId) {
        var query = """
                with latest_deployments as (
                    select r.id, r.artifact_version, r.deployed_at, r.psid as deployedby, r.chg_number, r.build_number,
                           r.release_notes, r.branch_url, r.eim_id, r.jira_ticket_id, r.component_id, cmp.name as component_name,
                           r.environment_id, env.name as evn_name,
                           r.created_at,
                           row_number() over (partition by r.environment_id order by r.created_at desc) as rn
                    from tbl_releases r
                    left join tbl_environments env on r.environment_id = env.id
                    left join tbl_components cmp on r.component_id = cmp.id
                    where r.eim_id = :eimId
                )
                select * from latest_deployments where rn = 1 order by component_id, created_at desc
                
                """;

        return databaseClient.sql(query)
                .bind("eimId", eimId)
                .map(d-> {
                    var data = new DeploymentHistory();
                    data.setId(d.get("id", Long.class));
                    data.setArtifactVersion(d.get("artifact_version", String.class));
                    data.setDeployedAt(d.get("deployed_at", LocalDateTime.class));
                    data.setDeployedBy(d.get("deployedby", String.class));
                    data.setChangeNumber(d.get("chg_number", String.class));
                    data.setBuildNumber(d.get("build_number", String.class));
                    data.setReleaseNotes(d.get("release_notes", String.class));
                    data.setBranchUrl(d.get("branch_url", String.class));
                    data.setEimId(d.get("eim_id", Long.class));
                    data.setJiraTicketId(d.get("jira_ticket_id", String.class));
                    data.setComponentId(d.get("component_id", Long.class));
                    data.setComponentName(d.get("component_name", String.class));
                    data.setEnvironmentId(d.get("environment_id", Long.class));
                    data.setEnvName(d.get("evn_name", String.class));
                    data.setRn(d.get("rn", Long.class));
                    return data;
                })
                .all()
                .groupBy(DeploymentHistory::getComponentName, x -> x)
                .flatMap(groupedFlux ->
                        groupedFlux.collectList()
                                .map(list -> Map.of(groupedFlux.key(), list))
                )
                .collectList()
                .map(listOfMaps -> {
                    Map<String, List<DeploymentHistory>> merged = new HashMap<>();
                    listOfMaps.forEach(merged::putAll);
                    return merged;
                });
    }

    public Mono<Map<String, List<DeploymentHistory>>> getHistory(long eimId) {
        var query = """
                select r.id,r.artifact_version , r.deployed_at , r.psid as deployedby,r. chg_number,r.build_number,r.release_notes,
                r.branch_url,r.eim_id,
                r.jira_ticket_id,r.component_id,cmp.name as component_name,r.environment_id,env.name as evn_name,
                row_number() over (partition by r.component_id order by r.created_at desc) as rn
                from tbl_releases r 
                    left join  tbl_environments env
                 on r.environment_id=env.id 
                left join tbl_components cmp
                 on r.component_id=cmp.id 
                where r.eim_id=:eimId order by r.component_id , r.created_at desc;
                
                """;
//        id | artifact_version | deployed_at | deployedby | chg_number | build_number | release_notes | jira_ticket_id | component_id | component_name | environment_id | evn_name | rn
        return databaseClient.sql(query)
                .bind("eimId", eimId)
                .map(d-> {
                    var data = new DeploymentHistory();
                    data.setId(d.get("id", Long.class));
                    data.setArtifactVersion(d.get("artifact_version", String.class));
                    data.setDeployedAt(d.get("deployed_at", LocalDateTime.class));
                    data.setDeployedBy(d.get("deployedby", String.class));
                    data.setChangeNumber(d.get("chg_number", String.class));
                    data.setBuildNumber(d.get("build_number", String.class));
                    data.setReleaseNotes(d.get("release_notes", String.class));
                    data.setBranchUrl(d.get("branch_url", String.class));
                    data.setEimId(d.get("eim_id", Long.class));
                    data.setJiraTicketId(d.get("jira_ticket_id", String.class));
                    data.setComponentId(d.get("component_id", Long.class));
                    data.setComponentName(d.get("component_name", String.class));
                    data.setEnvironmentId(d.get("environment_id", Long.class));
                    data.setEnvName(d.get("evn_name", String.class));
                    data.setRn(d.get("rn", Long.class));
                    return data;
                })
                .all()
                .groupBy(DeploymentHistory::getComponentName, x->x)
                .flatMap(groupedFlux ->
                        groupedFlux.collectList()
                                .map(list -> Map.of(groupedFlux.key(), list))
                )
                .collectList()
                .map(listOfMaps -> {
                    Map<String, List<DeploymentHistory>> merged = new HashMap<>();
                    listOfMaps.forEach(merged::putAll);
                    return merged;
                });


    }

    public Flux<ComponentDto> getComponent(long eimId) {
        return  componentRepository.findByEimId(eimId)
                .map(component -> {
                    var dto = new ComponentDto();
                    dto.setId(component.getId());
                    dto.setName(component.getName());
                    dto.setDescription(component.getDescription());
                    dto.setEimId(component.getEimId());
                    return dto;
                });
    }
    public Mono<Eim> getEim(long eimId) {
        return  eimRepository.findById(eimId);
    }
}


