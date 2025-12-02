package org.mustapha.smartShop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpSession;
import org.mustapha.smartShop.dto.request.LoginDtoRequest;
import org.mustapha.smartShop.model.User;
import org.mustapha.smartShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth") // Context path /api is already configured, so this becomes /api/v1/auth
@Tag(name = "Auth Controller", description = "Authentication APIs for login and logout")

public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Authenticates a user and stores the session with userId and role"
    )
    public ResponseEntity<?> login(@RequestBody LoginDtoRequest request, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (request.getPassword().equals(user.getPassword())) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("role", user.getRole().name());
                return ResponseEntity.ok(user.getRole() + " logged in");
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Clears the active session of the authenticated user"
    )
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}