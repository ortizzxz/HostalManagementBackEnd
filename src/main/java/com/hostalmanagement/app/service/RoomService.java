package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.hostalmanagement.app.HostalManagementApplication;
import com.hostalmanagement.app.DTO.RoomDTO;
import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.config.SecurityConfig;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Room;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.User.RolEnum;

public class RoomService {
    private final SecurityConfig securityConfig;

    private final HostalManagementApplication hostalManagementApplication;

    @Autowired
    private RoomDAO roomDAO;

    RoomService(HostalManagementApplication hostalManagementApplication, SecurityConfig securityConfig) {
        this.hostalManagementApplication = hostalManagementApplication;
        this.securityConfig = securityConfig;
    }

    public RoomDTO findRoomById(Long id){
        Room room = roomDAO.findById(id);
        return (room != null) ? toDTO(room) : null;
    }

    public List<RoomDTO> findAllRooms(){
        List<Room> rooms = roomDAO.findAll();
        return rooms.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private RoomDTO toDTO(Room room) {
        return new RoomDTO(room.getId(), room.getNumber(), room.getType(), room.getCapacity(), room.getBaseRate(), room.getState());
    }

    private Room toEntity(RoomDTO roomDTO) {
        return new Room(
                roomDTO.getNumber(),
                roomDTO.getType(), 
                roomDTO.getCapacity(),
                roomDTO.getBaseRate(),
                roomDTO.getState());
    }

    // public UserDTO updateUser(Long id, UserDTO userDTO) {
    //     User existingUser = userDAO.findById(id);
    //     if (existingUser != null) {
    //         existingUser.setName(userDTO.getName());
    //         existingUser.setLastname(userDTO.getLastname());
    //         existingUser.setEmail(userDTO.getEmail());
    //         existingUser.setRol(RolEnum.valueOf(userDTO.getRol())); 
            
    //         userDAO.update(existingUser);
    //         return toDTO(existingUser);
    //     }
    //     return null;
    // }

    // public boolean deleteUser(Long id) {
    //     User existingUser = userDAO.findById(id);
    //     if (existingUser != null) {
    //         userDAO.remove(id);
    //         return true;
    //     }
    //     return false;
    // }
}
