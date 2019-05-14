package com.murk.telegram.ping.handler.core.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Entity
@Table(name = "project_handler")
public class Project {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="p_id")
    @MapKey(name="key")
    private Map<String,Module> modules = new ConcurrentHashMap<>();

    public Project() {}

    public Project(String name) {
        this.name = name;
    }

    public void setModule(@NonNull Module module)
    {
        modules.put(module.getKey(),module);
    }

    public boolean containsModuleKey(@NonNull String moduleKey) {
        return modules.containsKey(moduleKey);
    }

    public Module getModule(@NonNull String moduleKey)
    {
        return modules.get(moduleKey);
    }
}
