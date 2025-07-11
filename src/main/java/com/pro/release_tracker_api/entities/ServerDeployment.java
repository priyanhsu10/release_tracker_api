package com.pro.release_tracker_api.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tbl_server_deployments")
@Setter
@Getter
public class ServerDeployment {
    @Id
    private long id;
    private String serverId;
    private String releaseId;

}
