package com.uraditi.backend.dto;

import com.uraditi.backend.dto.enums.UserStatusEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskerCategoryDto {
    UUID id;
    String firstName;
    String lastName;
    String email;
    String description;
    // todo slika??
    UserStatusEnum userStatus;
    Double rating;
    Double latitude;
    Double longitude;
    String phone;
    String city;
    Long categoryId;
    Double price;

    public TaskerCategoryDto(UUID id, String firstName, String lastName, String email, String description, UserStatusEnum userStatus, Double rating, Double latitude, Double longitude, String phone, String city, Long categoryId, Double price) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.userStatus = userStatus;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.city = city;
        this.categoryId = categoryId;
        this.price = price;
    }
}
