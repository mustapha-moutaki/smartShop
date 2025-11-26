package org.mustapha.smartShop.dto.response;

import org.mustapha.smartShop.enums.UserRole;

public class UserDtoResponse {

    private Long id;

    private String username;

    private UserRole role;

    private ClientDtoResponse client; // if it's client
}
