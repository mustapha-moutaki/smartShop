package org.mustapha.smartShop.dto.response;

import lombok.Data;
import org.mustapha.smartShop.enums.LoyaltyLevel;
import org.mustapha.smartShop.enums.UserRole;

import java.util.List;

@Data
public class ClientDtoResponse {

    private Long id;

    private String name;

    private String email;

    private LoyaltyLevel loyaltyLevel;

    private List<Long> orderIds;


    // avoid to retunr them in repsonse
//    // data related with user
//    private String username;
//    private UserRole role;


}
