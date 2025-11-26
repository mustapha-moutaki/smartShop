package org.mustapha.smartShop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.exception.ValidationException;
import org.mustapha.smartShop.mapper.ClientMapper;
import org.mustapha.smartShop.mapper.UserMapper;
import org.mustapha.smartShop.model.Client;
import org.mustapha.smartShop.model.User;
import org.mustapha.smartShop.repository.UserRepository;
import org.mustapha.smartShop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor // Use Lombok for constructor injection
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;

    @Override
    public UserDtoResponse createUser(UserDtoRequest dto) {
        // Check if username already exists
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ValidationException("Username already exists: " + dto.getUsername());
        }

        // Convert DTO to Entity
        User user = userMapper.toEntity(dto);

        // Handle client if exists in DTO
        if (dto.getClient() != null) {
            Client client = clientMapper.toEntity(dto.getClient());
            client.setUser(user);
            user.setClient(client);
        }

        // Save user to database
        User savedUser = userRepository.save(user);

        // Convert Entity to Response DTO
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) {
        // Find existing user by id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update basic user data
        user.setUsername(userDtoRequest.getUsername());
        user.setPassword(userDtoRequest.getPassword());
        user.setRole(userDtoRequest.getRole());

        // Update client data if exists in DTO
        if (userDtoRequest.getClient() != null) {
            if (user.getClient() != null) {
                // Update existing client entity
                clientMapper.updateClientFromDto(userDtoRequest.getClient(), user.getClient());
            } else {
                // Create new client entity
                Client client = clientMapper.toEntity(userDtoRequest.getClient());
                client.setUser(user);
                user.setClient(client);
            }
        }

        // Save updates to database
        User updatedUser = userRepository.save(user);

        // Convert updated entity to Response DTO
        return userMapper.toDto(updatedUser);
    }

    @Override
    public UserDtoResponse getUerById(Long id) {
        // Find user by id or throw exception
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Convert entity to Response DTO
        return userMapper.toDto(user);
    }


    @Override
    public List<UserDtoResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
      // find user in db
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found with id: "+id));
        userRepository.delete(user);
    }
}