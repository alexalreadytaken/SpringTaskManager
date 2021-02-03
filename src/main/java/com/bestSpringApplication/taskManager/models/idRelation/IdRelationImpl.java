package com.bestSpringApplication.taskManager.models.idRelation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "id_relation")
public class IdRelationImpl implements IdRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer db_id;
    private String id0;
    private String id1;

//    private Map<String,String> some = new HashMap<>(1);


    public IdRelationImpl(String id0, String id1) {
        this.id0 = id0;
        this.id1 = id1;
    }

    public IdRelationImpl() { }

    @Override
    public String getId0() {
        return id0;
    }

    @Override
    public String getId1() {
        return id1;
    }

    public void setId0(String id0) {
        this.id0 = id0;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public void setDb_id(Integer db_id) {
        this.db_id = db_id;
    }

    public Integer getDb_id() {
        return db_id;
    }
}
