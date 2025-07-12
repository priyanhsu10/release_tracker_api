package com.pro.release_tracker_api.dtos.ui;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Data
public class DeploymentHistory {
//    id | artifact_version | deployed_at | deployedby | chg_number | build_number | release_notes | jira_ticket_id | component_id | component_name | environment_id | evn_name | rn
//    r.branch_url,r.eim_id
    private long id;
    private String artifactVersion;
    private String EnvName;
    private LocalDateTime deployedAt;
    private String deployedTime;
    private String deployedBy;
    @Transient
    private Status status = Status.success;
    @Transient
    private String duration;
    private String jiraTicketId;
    private String branchUrl;
    @Transient
    private String gitCommitUrl=branchUrl;
    private String releaseNotes;
    private String buildNumber;
    private String changeNumber;
    private String componentName;
    private long componentId;
    private long environmentId;
    private long eimId;
    private long rn;

    public enum Status {
        success, failed, rollback
    }
}