package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, User> {

    Optional<User>findByUsername(String username);

}
