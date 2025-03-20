package com.hostalmanagement.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.RoomDTO;
import com.hostalmanagement.app.dao.RoomDAO;
import com.hostalmanagement.app.model.Room;

@Service
public class RoomService {

    @Autowired
    private RoomDAO roomDAO;


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

    public RoomDTO createRoom(RoomDTO roomDTO) {
        try{
            Room room = toEntity(roomDTO);
            roomDAO.save(room);
            return toDTO(room);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Error guardando la habitación: " + e.getMessage()); // ENUM CONST error (STATE)
        }
    }


    public RoomDTO updateRoom(Long id, RoomDTO roomDTO){
        Room existingRoom = roomDAO.findById(id);
        if(existingRoom != null){
            existingRoom.setNumber(roomDTO.getNumber());
            existingRoom.setBaseRate(roomDTO.getBaseRate());
            existingRoom.setCapacity(roomDTO.getCapacity());
            existingRoom.setType(roomDTO.getType());

            roomDAO.update(existingRoom);
            return toDTO(existingRoom);
        }

        return null;
    }

    public boolean deleteRoom(Long id){
        Room existingRoom = roomDAO.findById(id);
        if(existingRoom != null){
            roomDAO.delete(id);
            return true;
        }
        return false;
    }

}
