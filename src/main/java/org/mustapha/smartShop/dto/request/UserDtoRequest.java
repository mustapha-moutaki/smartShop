package org.mustapha.smartShop.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.mustapha.smartShop.enums.UserRole;

@Data
public class UserDtoRequest {

    @NotBlank(message = "username is required")
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "the password is required")
    @Size(min = 5, message = "password must be at least 5 characters")
    private String password;

    @NotNull(message = "role is required")
    private UserRole role;

    private ClientDtoRequest client;
}
