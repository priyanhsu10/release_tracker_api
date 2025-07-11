package com.pro.release_tracker_api.dtos;

import lombok.Data;

import java.util.List;
@Data
public class ReleaseWebhookDto {
    private Long EIMId;
    private String eimName;
    private String componentName;
    private String environmentName;
    private List<ServerDto> servers;
    private String artifactVersion;
    private String artifactUrl;
    private String jiraTicket;
    private String jenkinsJobId;
    private String psid;
    private String chgNumber;
    private String vultureUrl;
    private String releaseNotes;
    private String branchUrl;
    private String buildNumber;

    // getters and setters
@Data
    public static class ServerDto {
        private String hostname;
        private String ip;

        // getters and setters
    }
}
