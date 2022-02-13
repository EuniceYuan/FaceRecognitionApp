package com.backend.databaseservices;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
    @Query("select * from userprofile where fullname = :fullname")
    List<UserProfile> findByFullName(@Param("fullname") String fullname);

    @Query("select * from userprofile where email = :email")
    List<UserProfile> findByEmail(@Param("email") String email);

    @Query("select * from userprofile where email = :email OR fullname = :fullname")
    List<UserProfile> findByEmailOrFullname(@Param("email") String email, @Param("fullname") String fullname);
}
