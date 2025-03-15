package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.dao.UserDAO;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.User.RolEnum;

@Service
public class UserService {

    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private UserDAO userDAO;

    UserService(HostalManagementApplication hostalManagementApplication, SecurityConfig securityConfig) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    public UserDTO findUserById(Long id) {
        User user = userDAO.findById(id);
        return (user != null) ? toDTO(user) : null;
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userDAO.findAll();
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        String rolName = user.getRol() != null ? user.getRol().name().toUpperCase() : "UNKNOWN";
        return new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail(), user.getPassword(), rolName);
    }

    private User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                RolEnum.valueOf(userDTO.getRol()));
    }

    public UserDTO createUser(UserDTO userDTO) {
        try{
            User user = toEntity(userDTO);
            userDAO.save(user);
            return toDTO(user);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error creando el usuario: " + e.getMessage()); // ENUM CONST error (Role)
        }
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userDAO.findById(id);
        if (existingUser != null) {
            existingUser.setName(userDTO.getName());
            existingUser.setLastname(userDTO.getLastname());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setRol(RolEnum.valueOf(userDTO.getRol())); 
            
            userDAO.update(existingUser);
            return toDTO(existingUser);
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        User existingUser = userDAO.findById(id);
        if (existingUser != null) {
            userDAO.remove(id);
            return true;
        }
        return false;
    }

}
