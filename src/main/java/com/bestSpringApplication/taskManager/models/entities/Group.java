package com.bestSpringApplication.taskManager.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@ToString(exclude = "users")
@JsonIgnoreProperties(value = "users")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(targetEntity = User.class,fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id",referencedColumnName = "id")
    private List<User> users;
}
