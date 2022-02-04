package com.uraditi.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uraditi.backend.dto.enums.TaskStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class TaskEntity {

    @Id
    @Column(name = "task_id")
    @Basic
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    CategoryEntity category;

    //    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    UserEntity client;

    //    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tasker_id", referencedColumnName = "user_id")
    UserEntity tasker;

    @Column(name = "time_created")
    Timestamp timeCreated;

    @Column(name = "time_started")
    Timestamp timeStarted;

    @Column(name = "time_finished")
    Timestamp timeFinished;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    TaskStatusEnum taskStatus;

    // rating that the tasker got for the job done
    @Column(name = "tasker_rating")
    Double taskerRating;

    @Column(name = "latitude")
    Double latitude;

    @Column(name = "longitude")
    Double longitude;

    // review that the tasker got for the job done
    @Column(name = "tasker_review")
    String taskerReview;

    // review that the client got for their job
    @Column(name = "client_review")
    String clientReview;
}
