package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

        Optional<User>findByUsername(String username);

        User findById(Long id);
}
