package com.pro.release_tracker_api.dtos;

import com.pro.release_tracker_api.entities.*;
import lombok.Data;

import java.util.List;
@Data
public class CombineResult {
    private Eim eim;
    private Component component;
    private Environment environment;
    private List<Server> servers;
    private List<ServerDeployment> serverDeployments ;

}
