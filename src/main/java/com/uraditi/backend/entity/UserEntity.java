package com.uraditi.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "id")
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
