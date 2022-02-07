package com.uraditi.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category_tasker")
public class CategoryTaskerEntity {
    @Id
    @Column(name = "category_tasker_id")
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity tasker;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    // price that tasker has for given category
    @Column(name = "price")
    Double price;
}
