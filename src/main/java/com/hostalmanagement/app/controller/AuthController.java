package com.hostalmanagement.app.controller;

import com.hostalmanagement.app.DTO.UserDTO;
import com.hostalmanagement.app.service.JwtService;
import com.hostalmanagement.app.service.UserService;
import com.hostalmanagement.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) throws Exception {
        // Authenticate the user (check if email and password match)
        User user = userService.authenticateUser(userDTO); // Use the service to validate the credentials
        if (user == null) {
            throw new Exception("Invalid credentials");
        }

        // Generate JWT token for the user with email and tenant ID
        String token = jwtService.generateToken(user.getEmail(), user.getTenant().getId(), user.getRol().toString());

        // Return the token as the response body
        return ResponseEntity.ok(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UserDTO userDTO) {
        boolean emailSent = userService.sendPasswordResetEmail(userDTO.getEmail());
        // Always return success for security (do not reveal if email exists)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody UserDTO userDTO) {
        boolean success = userService.resetPassword(token, userDTO.getPassword());
        if (!success) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        return ResponseEntity.ok().build();
    }

}
