package org.mustapha.smartShop.service;

import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;

import java.util.List;

public interface PromoCodeService {

    PromoCodeDtoResponse createPromoCode(PromoCodeDtoRequest dto);

    List<PromoCodeDtoResponse> getAllPromoCodes();

    PromoCodeDtoResponse getPromoCodeById(Long id);

    PromoCodeDtoResponse updatePromoCode(Long id, PromoCodeDtoRequest dto);

    void deletePromoCode(Long id);

    PromoCodeDtoResponse getPromoCodeByCode(String code);
}
