package com.finos.ekyc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finos.ekyc.demo.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
