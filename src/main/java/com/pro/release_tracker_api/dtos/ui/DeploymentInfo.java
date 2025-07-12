package com.pro.release_tracker_api.dtos.ui;


import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class DeploymentInfo {
    private String artifactUrl;
    private String deployedAt;
    private String deployedTime;
    private String deployedBy;
    private String gitBranchUrl;
    private String gitCommitUrl;
    private String releaseNotes;
    private String buildNumber;
//    private String approvedBy;
    private String changeNumber;
    private long id;
    private String artifactVersion;
    private String evn_name;
    @Transient
    private DeploymentHistory.Status status = DeploymentHistory.Status.success;
    @Transient
    private String duration;
    private String jiraTicketId;
    private String branch_url;
;
    private String componentName;
    private String evnName;
    private long componentId;
    private long environmentId;
    private long eimId;
    private long rn;
}