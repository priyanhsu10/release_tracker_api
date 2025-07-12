package com.pro.release_tracker_api.services;

import com.pro.release_tracker_api.dtos.ui.DeploymentHistory;
import com.pro.release_tracker_api.entities.Eim;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DatabaseClient databaseClient;

    public Flux<Eim> searchEim(String name) {


        return databaseClient.sql("SELECT * FROM tbl_eims WHERE name like % :name %")
                .bind("name", name)
                .mapValue(Eim.class)
                .all();
    }

    public Flux<GroupedFlux<String, DeploymentHistory>> getLatestDeployment(long eimId) {
var query = """
        with latest_deployments as (
            select r.id, r.artifact_version, r.deployed_at, r.psid as deployedby, r.chg_number, r.build_number,
                   r.release_notes, r.branch_url, r.eim_id, r.jira_ticket_id, r.component_id, cmp.name as component_name,
                   r.environment_id, env.name as evn_name,
                   r.created_at,
                   row_number() over (partition by r.component_id order by r.created_at desc) as rn
            from tbl_releases r
            left join tbl_environments env on r.environment_id = env.id
            left join tbl_components cmp on r.component_id = cmp.id
            where r.eim_id = :eimId
        )
        select * from latest_deployments where rn = 1 order by component_id, created_at desc
        
        """;

        return databaseClient.sql(query)
                .bind("eimId", eimId)
                .mapValue(DeploymentHistory.class)
                .all()
                .groupBy(DeploymentHistory::getComponentName, x -> x);
    }

    public Flux<GroupedFlux<String, DeploymentHistory>> getHistory(long eimId) {
        var query = """
                select r.id,r.artifact_version , r.deployed_at , r.psid as deployedby,r. chg_number,r.build_number,r.release_notes,
                r.branch_url,r.eim_id
                r.jira_ticket_id,r.component_id,cmp.name as component_name,r.environment_id,env.name as evn_name
                row_number() over (partition by r.component_id order by r.created_at desc) as rn
                from tbl_releases r left join tbl_environments env
                 on r.environment_id=env.id left join tbl_components cmp
                 on r.component_id=cmp.id where r.eim_id=:eimId order by r.component_id , r.created_at desc
                
                """;
//        id | artifact_version | deployed_at | deployedby | chg_number | build_number | release_notes | jira_ticket_id | component_id | component_name | environment_id | evn_name | rn
        return databaseClient.sql(query)
                .bind("eimId", eimId)
                .mapValue(DeploymentHistory.class)
                .all()
                .groupBy(DeploymentHistory::getComponentName, x -> x) ;


    }
}


