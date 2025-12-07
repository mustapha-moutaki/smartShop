package org.mustapha.smartShop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.mustapha.smartShop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
// add pagiantaion
@Tag(name = "Users", description = "User management endpoints")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create new user", description = "Admin can create new users")
    public ResponseEntity<UserDtoResponse>createUser(@Valid @RequestBody UserDtoRequest userDtoRequest){
        UserDtoResponse createdUser =  userService.createUser(userDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Admin updates an existing user")
    public ResponseEntity<UserDtoResponse>updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDtoRequest userDtoRequest
    )
    {
        UserDtoResponse updatedUser = userService.updateUser(id, userDtoRequest);
        return ResponseEntity.ok(updatedUser);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by ID")
    public ResponseEntity<UserDtoResponse>getById( @PathVariable Long id){
        UserDtoResponse userFind = userService.getUerById(id);
        return ResponseEntity.ok(userFind);
    }

    @GetMapping
    @Operation(summary = "Delete user", description = "Admin deletes user by ID")
    public ResponseEntity<Page<UserDtoResponse>>getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<UserDtoResponse> users = userService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Admin deletes user by ID")
    public ResponseEntity<UserDtoResponse>deleteUser(@PathVariable Long id){

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();

    }

}
