package com.uraditi.backend.repository;

import com.uraditi.backend.dto.TaskerCategoryDto;
import com.uraditi.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    List<UserEntity> findAll();

    @Query(value = "select new com.uraditi.backend.dto.TaskerCategoryDto(u.id, u.firstName, u.lastName, u.email, u.description," +
            " u.userStatus, u.rating, u.latitude, u.longitude, u.phone, u.city, ct.category.id, ct.price) " +
            "from UserEntity u " +
            "join CategoryTaskerEntity ct " +
            "on ct.tasker = u " +
            "where u.city= :city " +
            "and ct.category.id= :categoryId")
    List<TaskerCategoryDto> findTaskersByCategoryAndCityDto(@Param("categoryId") Long categoryId,
                                                            @Param("city") String city);

    @Query(value = "select new com.uraditi.backend.dto.TaskerCategoryDto(u.id, u.firstName, u.lastName, u.email, u.description," +
            " u.userStatus, u.rating, u.latitude, u.longitude, u.phone, u.city, ct.category.id, ct.price) " +
            "from UserEntity u " +
            "join CategoryTaskerEntity ct " +
            "on ct.tasker = u " +
            "where u.city= :city " +
            "and ct.category.id= :categoryId " +
            "order by u.rating DESC NULLS LAST")
    List<TaskerCategoryDto> findTaskersByCategoryAndCityDtoOrderedByRating(@Param("categoryId") Long categoryId,
                                                                           @Param("city") String city);

    @Query(value = "select new com.uraditi.backend.dto.TaskerCategoryDto(u.id, u.firstName, u.lastName, u.email, u.description," +
            " u.userStatus, u.rating, u.latitude, u.longitude, u.phone, u.city, ct.category.id, ct.price) " +
            "from UserEntity u " +
            "join CategoryTaskerEntity ct " +
            "on ct.tasker = u " +
            "where u.city= :city " +
            "and ct.category.id= :categoryId " +
            "order by ct.price ASC")
    List<TaskerCategoryDto> findTaskersByCategoryAndCityDtoOrderedByPrice(@Param("categoryId") Long categoryId,
                                                                          @Param("city") String city);

    @Query(value = "select new com.uraditi.backend.dto.TaskerCategoryDto(u.id, u.firstName, u.lastName, u.email, u.description," +
            " u.userStatus, u.rating, u.latitude, u.longitude, u.phone, u.city, ct.category.id, ct.price) " +
            "from UserEntity u " +
            "join CategoryTaskerEntity ct " +
            "on ct.tasker = u " +
            "where u.city= :city " +
            "and ct.category.id= :categoryId " +
            "order by u.numberOfTasks desc nulls last")
    List<TaskerCategoryDto> findTaskersByCategoryAndCityDtoOrderedByNumberOfTasks(@Param("categoryId") Long categoryId,
                                                                                  @Param("city") String city);

    Optional<UserEntity> findByEmail(@Param("email") String email);

}
