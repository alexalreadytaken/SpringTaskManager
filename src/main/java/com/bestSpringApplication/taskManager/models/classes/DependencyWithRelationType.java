package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.enums.RelationType;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DependencyWithRelationType extends DependencyImpl{
    private RelationType relationType;

    public DependencyWithRelationType(RelationType relationType,String id0, String id1) {
        super(id0, id1);
        this.relationType=relationType;
    }
}
