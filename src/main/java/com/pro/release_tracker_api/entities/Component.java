package com.pro.release_tracker_api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tbl_components")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
 public class Component {
    @Id
    private long id;
    private String name;
    private String description;
    private long eimId; // Foreign key to Eims table
}
