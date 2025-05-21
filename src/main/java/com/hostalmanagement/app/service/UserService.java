package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.dao.TenantDAO;
import com.hostalmanagement.app.dao.UserDAO;
import com.hostalmanagement.app.model.Tenant;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.User.RolEnum;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TenantDAO tenantDAO;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDTO findUserById(Long id) {
        User user = userDAO.findById(id);
        return (user != null) ? toDTO(user) : null;
    }

    public List<UserDTO> findAllUsers(Tenant tenant) {
        List<User> users = userDAO.findAll(tenant);
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    protected UserDTO toDTO(User user) {
        String rolName = user.getRol() != null ? user.getRol().name().toUpperCase() : "UNKNOWN";
        Long tenantId = user.getTenant() != null ? user.getTenant().getId() : null;
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                rolName,
                tenantId);
    }

    protected User toEntity(UserDTO userDTO) {
        Tenant tenant = tenantDAO.findById(userDTO.getTenant());
        if (tenant == null) {
            throw new IllegalArgumentException("Tenant ID not found: " + userDTO.getTenant());
        }
        return new User(
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                RolEnum.valueOf(userDTO.getRol()),
                tenant);
    }

    public UserDTO createUser(UserDTO userDTO) {
        try {
            User user = toEntity(userDTO);
            userDAO.save(user);
            return toDTO(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error creando el usuario: " + e.getMessage());
        }
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userDAO.findById(id);
        if (existingUser != null) {

            Tenant tenant = tenantDAO.findById(userDTO.getTenant());
            if (tenant == null) {
                throw new IllegalArgumentException("Tenant ID not found: " + userDTO.getTenant());
            }

            existingUser.setName(userDTO.getName());
            existingUser.setLastname(userDTO.getLastname());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setRol(RolEnum.valueOf(userDTO.getRol().toLowerCase()));
            existingUser.setTenant(tenant);

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

    // Authenticate the user by email and password
    public User authenticateUser(UserDTO userDTO) {
        // Find the user by email
        User user = userDAO.findByEmail(userDTO.getEmail());
        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            // If the user is found and password matches, return the user
            return user;
        }
        // If the user is not found or password doesn't match, return null
        return null;
    }

    // Inside UserService.java
    public String loginUser(UserDTO userDTO) throws Exception {
        User user = authenticateUser(userDTO);
        if (user == null) {
            throw new Exception("Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail(), user.getTenant().getId(), user.getRol().toString());
    }

}
