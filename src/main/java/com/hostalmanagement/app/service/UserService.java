package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.dao.UserDAO;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.User.RolEnum;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDTO findUserById(Long id) {
        User user = userDAO.findById(id);
        return toDTO(user);
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userDAO.findAll();
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        String rolName = user.getRol() != null ? user.getRol().name().toUpperCase() : "UNKNOWN";
        return new UserDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail(), rolName);
    }

    private User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                null,
                RolEnum.valueOf(userDTO.getRol()));
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        userDAO.save(user);
        return toDTO(user);
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
