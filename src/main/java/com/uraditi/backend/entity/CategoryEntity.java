package com.uraditi.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class CategoryEntity {

    @Id
    @Column(name = "category_id")
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "licence")
    Boolean licence;

    @OneToMany(mappedBy = "category")
    Set<CategoryTaskerEntity> categoryTaskerEntities;

    @Column(name = "description")
    String description;
}
