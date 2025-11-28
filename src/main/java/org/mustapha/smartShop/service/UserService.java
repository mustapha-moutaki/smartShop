package org.mustapha.smartShop.service;

import jakarta.transaction.Transactional;
import org.mustapha.smartShop.dto.request.UserDtoRequest;
import org.mustapha.smartShop.dto.response.UserDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*8
 All methods may throw exceptions handled by GlobalExceptionHandler
 */
public interface UserService {

    UserDtoResponse createUser(UserDtoRequest dto);
    UserDtoResponse updateUser(Long id, UserDtoRequest userDtoRequest);
    UserDtoResponse getUerById(Long id);
    Page<UserDtoResponse> getAllUsers(int page, int size);
    void deleteUser(Long id);
}

