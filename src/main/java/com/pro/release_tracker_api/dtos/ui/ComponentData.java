package com.pro.release_tracker_api.dtos.ui;

import lombok.Data;

import java.util.Map;

@Data
public class ComponentData {
    private String id;
    private String name;
    private String description;
    private String repository;
    private String owner;
    private Map<String, DeploymentInfo> deployments;
}
