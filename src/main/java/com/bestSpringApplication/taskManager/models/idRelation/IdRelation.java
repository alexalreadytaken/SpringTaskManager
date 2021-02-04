package com.bestSpringApplication.taskManager.models.idRelation;

import com.bestSpringApplication.taskManager.handlers.ModifiableSingletonMap;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Map;

@Entity(name = "id_relation")
public class IdRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer db_id;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "relations_map")
    @MapKeyColumn(name = "id0")
    @Column(name = "id1")
    private Map<String,String> relation;

    public IdRelation(String id0, String id1) {
        relation = new ModifiableSingletonMap<>();
        relation.put(id0,id1);
    }

    public IdRelation() { }

   /* @Override
    public String getId0() {
        return null;
    }

    @Override
    public String getId1() {
        return null;
    }*/


    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public Integer getDb_id() {
        return db_id;
    }
}
