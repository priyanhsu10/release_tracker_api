package com.pro.release_tracker_api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tbl_eims")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Eim {
    @Id
    private long id;
    private long eimNumber; // Unique identifier for the EIM
    private String name;
    private String description;


}

