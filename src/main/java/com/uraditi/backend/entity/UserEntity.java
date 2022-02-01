package com.uraditi.backend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @Basic
    private String id;

    @Column(name = "email")
    private String email;
}
