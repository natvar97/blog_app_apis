package com.indialone.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.indialone.blogapp.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

}
