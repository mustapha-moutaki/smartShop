package org.mustapha.smartShop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.mustapha.smartShop.enums.UserRole;
import org.mustapha.smartShop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDtoRequest userDtoRequest;
    private UserDtoResponse userDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize request
        userDtoRequest = new UserDtoRequest();
        userDtoRequest.setUsername("testuser");
        userDtoRequest.setPassword("password123");
        userDtoRequest.setRole(UserRole.CLIENT);

        // Initialize response
        userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(1L);
        userDtoResponse.setUsername("testuser");
        userDtoResponse.setRole(UserRole.CLIENT);
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(userDtoRequest)).thenReturn(userDtoResponse);

        ResponseEntity<UserDtoResponse> response = userController.createUser(userDtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("testuser", response.getBody().getUsername());
        verify(userService, times(1)).createUser(userDtoRequest);
    }

    @Test
    void testUpdateUser() {
        when(userService.updateUser(1L, userDtoRequest)).thenReturn(userDtoResponse);

        ResponseEntity<UserDtoResponse> response = userController.updateUser(1L, userDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testuser", response.getBody().getUsername());
        verify(userService, times(1)).updateUser(1L, userDtoRequest);
    }

    @Test
    void testGetById() {
        when(userService.getUerById(1L)).thenReturn(userDtoResponse);

        ResponseEntity<UserDtoResponse> response = userController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).getUerById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<UserDtoResponse> userList = new ArrayList<>();
        userList.add(userDtoResponse);

        Page<UserDtoResponse> userPage = new PageImpl<>(userList);

        when(userService.getAllUsers(0, 10)).thenReturn(userPage);

        ResponseEntity<Page<UserDtoResponse>> response = userController.getAllUsers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(userService, times(1)).getAllUsers(0, 10);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<UserDtoResponse> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }
}
