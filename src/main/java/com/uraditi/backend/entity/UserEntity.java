package com.uraditi.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uraditi.backend.dto.enums.UserStatusEnum;
import com.uraditi.backend.dto.enums.UserTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @Basic
    UUID id;

    @Column(name = "email")
    String email;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<TaskEntity> tasksAsClient;

    @JsonManagedReference
    @OneToMany(mappedBy = "tasker", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<TaskEntity> tasksAsTasker;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    UserTypeEnum userType;

    @Column(name = "description")
    String description;

    // todo slika??

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    UserStatusEnum userStatus;

    @Column(name = "rating")
    Double rating;

    @Column(name = "latitude")
    Double latitude;

    @Column(name = "longitude")
    Double longitude;

    @Column(name = "phone")
    String phone;

    @Column(name = "city")
    String city;

    @OneToMany(mappedBy = "tasker")
    Set<CategoryTaskerEntity> categoryTaskerEntities;
}
