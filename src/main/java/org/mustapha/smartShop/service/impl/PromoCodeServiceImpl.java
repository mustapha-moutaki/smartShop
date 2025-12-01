package org.mustapha.smartShop.service.impl;

import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;
import org.mustapha.smartShop.mapper.PromoCodeMapper;
import org.mustapha.smartShop.model.PromoCode;
import org.mustapha.smartShop.repository.PromoCodeRepository;
import org.mustapha.smartShop.service.PromoCodeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public PromoCodeDtoResponse createPromoCode(PromoCodeDtoRequest dto) {
        PromoCode promoCode = promoCodeMapper.toEntity(dto);
        PromoCode saved = promoCodeRepository.save(promoCode);
        return promoCodeMapper.toDto(saved);
    }

    @Override
    public List<PromoCodeDtoResponse> getAllPromoCodes() {
        return promoCodeRepository.findAll().stream()
                .map(promoCodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PromoCodeDtoResponse getPromoCodeById(Long id) {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PromoCode not found"));
        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeDtoResponse updatePromoCode(Long id, PromoCodeDtoRequest dto) {
        PromoCode existing = promoCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PromoCode not found"));
        existing.setCode(dto.getCode());
        existing.setPercentage(dto.getPercentage());
        existing.setActive(dto.isActive());
        return promoCodeMapper.toDto(promoCodeRepository.save(existing));
    }

    @Override
    public void deletePromoCode(Long id) {
        promoCodeRepository.deleteById(id);
    }

    @Override
    public PromoCodeDtoResponse getPromoCodeByCode(String code) {
        PromoCode promoCode = promoCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("PromoCode not found"));
        return promoCodeMapper.toDto(promoCode);
    }
}
