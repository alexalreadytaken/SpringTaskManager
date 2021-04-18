package com.bestSpringApplication.taskManager.models.classes;

import com.bestSpringApplication.taskManager.models.enums.RelationType;
import com.bestSpringApplication.taskManager.models.interfaces.Dependency;
import lombok.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DependencyWithRelationType extends DependencyImpl implements Dependency{
    private RelationType relationType;

    public DependencyWithRelationType(RelationType relationType,String id0, String id1) {
        super(id0, id1);
        this.relationType=relationType;
    }
}
