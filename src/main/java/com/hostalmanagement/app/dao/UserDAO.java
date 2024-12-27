package com.hostalmanagement.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hostalmanagement.app.model.User;

@Repository
public interface UserDAO {
    User findById(Long id);
    List<User> findAll();
    List<User> findByLastname(String lastname);
    User findByEmail(String email);  
    List<User> findByRol(String rol);

    void save(User user);
    void update(User user);
    void remove(Long id);
}
