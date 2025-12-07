package org.mustapha.smartShop.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.aspectj.weaver.ast.Not;
import org.mustapha.smartShop.enums.LoyaltyLevel;
import org.mustapha.smartShop.enums.UserRole;

import java.util.List;

@Data
public class ClientDtoRequest {

    @NotBlank(message = "name is required")
    @NotNull
    private String name;

    @NotBlank(message = "email is required")
    @Email
    private String email;

    private LoyaltyLevel loyaltyLevel = LoyaltyLevel.BRONZE;// default


    private List<Long> orderIds;


}
