package com.pro.release_tracker_api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tbl_servers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Server {
    @Id
    private long id;
    private String hostname;
    private String ipAddress;
    private long environmentId; // Foreign key to Components table
    private String description;
}
