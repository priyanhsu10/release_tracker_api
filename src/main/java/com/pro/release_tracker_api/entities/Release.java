package com.pro.release_tracker_api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("tbl_releases")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Release {
    @Id
    private long id;
    private String artifactVersion;
    private String artifactUrl;
    private String jiraTicketId;
    private long componentId; // Foreign key to Components table
    private long environmentId; // Foreign key to Environment table
    private String jenkinsJobId;
    private String psid;
    private String chgNumber;
    private String vultureUrl;
    private String releaseNotes;
    private String branchUrl;
    private LocalDateTime CreatedAt;
    private String buildNumber;
    private long eimId; // Foreign key to Eims table

}
