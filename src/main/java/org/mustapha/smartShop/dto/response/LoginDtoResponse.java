package org.mustapha.smartShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class LoginDtoResponse {
    private Long id;
    private String username;
    private String role;
    private String message;
}
