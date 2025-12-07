package org.mustapha.smartShop.repository;

import org.mustapha.smartShop.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    //find by code
    Optional<PromoCode> findByCode(String code);

    boolean existsByCode(String code);
}
