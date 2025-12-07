package org.mustapha.smartShop.dto.response;

import lombok.Data;
import org.mustapha.smartShop.enums.UserRole;

@Data
public class UserDtoResponse {

    private Long id;

    private String username;

    private UserRole role;

    private ClientDtoResponse client; // if it's client
}
