package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Entity
@Table(name = "project_handler", schema = "public")
public class Project {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name="p_id")
    private Set<Module> modules = ConcurrentHashMap.newKeySet();

    public Project() {}

    public Project(String name) {
        this.name = name;
    }

    public void setModule(@NonNull String moduleKey)
    {
        modules.add(new Module(moduleKey));
    }

    public boolean containsModule(@NonNull String moduleKey) {
        return modules.contains(new Module(moduleKey));
    }

}
