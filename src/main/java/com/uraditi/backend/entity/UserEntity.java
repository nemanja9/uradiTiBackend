package com.uraditi.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "public")
public class UserEntity {

    @Id
    @Column(name = "id")
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
