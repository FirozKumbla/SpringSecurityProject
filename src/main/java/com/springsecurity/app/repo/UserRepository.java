package com.springsecurity.app.repo;

import com.springsecurity.app.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUserName(String userName);
}
