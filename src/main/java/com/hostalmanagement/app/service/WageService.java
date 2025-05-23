package com.hostalmanagement.app.service;

import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.DTO.WageDTO;
import com.hostalmanagement.app.model.User;
import com.hostalmanagement.app.model.Wage;
import com.hostalmanagement.app.dao.UserDAO;
import com.hostalmanagement.app.dao.WageDAO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WageService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WageDAO wageDAO;

    @Autowired
    private UserService userService;

    // find all wages
    public List<WageDTO> findAllWages() {
        List<Wage> wages = wageDAO.findAll();

        return wages.stream()
                .map(this::toDTO)
                .toList();
    }

    // find by user id
    public List<WageDTO> findWagesByUserId(Long userId) {
        List<Wage> wages = wageDAO.findByUserId(userId);
        return wages.stream()
                .map(this::toDTO)
                .toList();
    }

    public WageDTO findWageById(Long id) {
        Wage existingWage = wageDAO.findById(id);

        if (existingWage != null) {
            WageDTO wageDTO = toDTO(existingWage);
            return wageDTO;
        } else {
            throw new IllegalArgumentException("Wage not found by ID" + id);
        }
    }

    public List<WageDTO> findWagesByTenantId(Long tenantId) {
        return wageDAO.findByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Create a new Wage
    @Transactional
    public WageDTO createWage(WageDTO wageDTO) {
        if (wageDTO.getUserDTO() == null || wageDTO.getUserDTO().getId() == null) {
            throw new IllegalArgumentException("UserDTO and User ID must be provided");
        }
        // Ensure the user exists before creating a Wage
        User user = userDAO.findById(wageDTO.getUserDTO().getId());

        if (user == null) {
            throw new IllegalArgumentException("User with ID " + wageDTO.getUserDTO().getId() + " does not exist.");
        }

        // Check if wage already exists for this user
        Wage existingWage = wageDAO.findByUser(user);
        if (existingWage != null) {
            throw new IllegalArgumentException("Wage for user ID " + user.getId() + " already exists.");
        }

        // Convert WageDTO to Wage entity and set the User
        Wage wage = toEntity(wageDTO);
        wage.setUser(user); // Link the user to the wage

        // Save the wage
        wageDAO.save(wage);

        // Return the WageDTO after saving
        return toDTO(wage);
    }

    public WageDTO updateWage(Long id, WageDTO wageDTO) {
        Wage existingWage = wageDAO.findById(id);
        if (existingWage == null)
            return null;

        existingWage.setHourRate(wageDTO.getHourRate());
        existingWage.setExtraPayments(wageDTO.getExtraPayments());
        existingWage.setTaxImposed(wageDTO.getTaxImposed());
        existingWage.setWeeklyHours(wageDTO.getWeeklyHours());

        // Optional: ensure you're updating the correct associated user
        if (wageDTO.getUserDTO() != null && wageDTO.getUserDTO().getId() != null) {
            User user = userService.toEntity(wageDTO.getUserDTO());
            existingWage.setUser(user);
        }

        wageDAO.update(existingWage);
        return toDTO(existingWage);
    }

    // Helper method to convert WageDTO to Wage entity
    private Wage toEntity(WageDTO wageDTO) {
        // Assuming we have the other necessary fields from WageDTO (like extraPayments,
        // taxImposed, etc.)
        Wage wage = new Wage();
        wage.setExtraPayments(wageDTO.getExtraPayments());
        wage.setHourRate(wageDTO.getHourRate());
        wage.setTaxImposed(wageDTO.getTaxImposed());
        wage.setWeeklyHours(wageDTO.getWeeklyHours());
        // We don't need to set the user here, because it's set separately
        return wage;
    }

    // Helper method to convert Wage entity to WageDTO
    private WageDTO toDTO(Wage wage) {
        // Convert Wage entity back to WageDTO (assuming you need a full DTO response)
        WageDTO wageDTO = new WageDTO();
        wageDTO.setId(wage.getId());
        wageDTO.setExtraPayments(wage.getExtraPayments());
        wageDTO.setHourRate(wage.getHourRate());
        wageDTO.setTaxImposed(wage.getTaxImposed());
        wageDTO.setWeeklyHours(wage.getWeeklyHours());
        // Assuming the userDTO has a simple mapping
        UserDTO userDTO = userService.toDTO(wage.getUser());
        wageDTO.setUserDTO(new UserDTO(userDTO.getId(), userDTO.getName(), userDTO.getLastname(), userDTO.getEmail(),
                userDTO.getPassword(), userDTO.getRol(), userDTO.getTenant())); // You would implement the UserDTO
                                                                                // constructor accordingly
        return wageDTO;
    }
}
