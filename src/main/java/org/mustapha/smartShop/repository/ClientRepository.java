package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Long, Client> {
}
