package com.example.LoginTask.repository;

import com.example.LoginTask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select en from User en where  en.name = :name")
    List<User> getUserByName(String name);
    @Query("select en from User en where  en.id = :id")
    User getByUserId(Integer id);
}