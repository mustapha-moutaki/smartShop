package org.mustapha.smartShop.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.mustapha.smartShop.exception.ResourceNotFoundException;
import org.mustapha.smartShop.exception.ValidationException;
import org.mustapha.smartShop.mapper.UserMapper;
import org.mustapha.smartShop.model.User;
import org.mustapha.smartShop.repository.UserRepository;
import org.mustapha.smartShop.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
//@RequiredArgsConstructor instead of nitialize them

public class UserServiceImpl implements UserService {

    // inject by contstruct
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // initialze them
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDtoResponse createUser(UserDtoRequest dto) {
        Optional<User> userInfo = userRepository.findByUsername(dto.getUsername());

        if (userInfo.isPresent()) {
            throw new ValidationException("Username already exists: " + dto.getUsername());
        }

        // from dto to entity
        User user = userMapper.toEntity(dto);

        // Save user
        User savedUser = userRepository.save(user);

        // Map entity to response DTO
        return userMapper.toDto(user);

    }

    @Override
    public UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest) {
        Optional <User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("user it")
        }
    }

    @Override
    public UserDtoResponse getUerById(Long id) {
        return null;
    }

    @Override
    public List<UserDtoResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(Long id) {

    }
}
