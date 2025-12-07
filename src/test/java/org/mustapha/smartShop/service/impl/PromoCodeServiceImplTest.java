package org.mustapha.smartShop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;
import org.mustapha.smartShop.mapper.PromoCodeMapper;
import org.mustapha.smartShop.model.PromoCode;
import org.mustapha.smartShop.repository.PromoCodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromoCodeServiceImplTest {

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoCodeMapper promoCodeMapper;

    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    private PromoCode promoCode;
    private PromoCodeDtoRequest promoCodeDtoRequest;
    private PromoCodeDtoResponse promoCodeDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        promoCodeDtoRequest = new PromoCodeDtoRequest();
        promoCodeDtoRequest.setCode("DISCOUNT10");
        promoCodeDtoRequest.setPercentage(10);
        promoCodeDtoRequest.setActive(true);

        promoCode = new PromoCode();
        promoCode.setId(1L);
        promoCode.setCode("DISCOUNT10");
        promoCode.setPercentage(10);
        promoCode.setActive(true);

        promoCodeDtoResponse = new PromoCodeDtoResponse();
        promoCodeDtoResponse.setId(1L);
        promoCodeDtoResponse.setCode("DISCOUNT10");
        promoCodeDtoResponse.setPercentage(10);
        promoCodeDtoResponse.setActive(true);
    }

    @Test
    void testCreatePromoCode() {
        when(promoCodeMapper.toEntity(promoCodeDtoRequest)).thenReturn(promoCode);
        when(promoCodeRepository.save(promoCode)).thenReturn(promoCode);
        when(promoCodeMapper.toDto(promoCode)).thenReturn(promoCodeDtoResponse);

        PromoCodeDtoResponse response = promoCodeService.createPromoCode(promoCodeDtoRequest);

        assertNotNull(response);
        assertEquals("DISCOUNT10", response.getCode());
        verify(promoCodeRepository, times(1)).save(promoCode);
    }

    @Test
    void testGetAllPromoCodes() {
        List<PromoCode> list = new ArrayList<>();
        list.add(promoCode);

        when(promoCodeRepository.findAll()).thenReturn(list);
        when(promoCodeMapper.toDto(promoCode)).thenReturn(promoCodeDtoResponse);

        List<PromoCodeDtoResponse> response = promoCodeService.getAllPromoCodes();

        assertEquals(1, response.size());
        assertEquals("DISCOUNT10", response.get(0).getCode());
    }

    @Test
    void testGetPromoCodeByIdSuccess() {
        when(promoCodeRepository.findById(1L)).thenReturn(Optional.of(promoCode));
        when(promoCodeMapper.toDto(promoCode)).thenReturn(promoCodeDtoResponse);

        PromoCodeDtoResponse response = promoCodeService.getPromoCodeById(1L);

        assertNotNull(response);
        assertEquals("DISCOUNT10", response.getCode());
    }

    @Test
    void testGetPromoCodeByIdNotFound() {
        when(promoCodeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> promoCodeService.getPromoCodeById(1L));
    }

    @Test
    void testUpdatePromoCodeSuccess() {
        when(promoCodeRepository.findById(1L)).thenReturn(Optional.of(promoCode));
        when(promoCodeRepository.save(promoCode)).thenReturn(promoCode);
        when(promoCodeMapper.toDto(promoCode)).thenReturn(promoCodeDtoResponse);

        PromoCodeDtoResponse response = promoCodeService.updatePromoCode(1L, promoCodeDtoRequest);

        assertNotNull(response);
        assertEquals("DISCOUNT10", response.getCode());
        verify(promoCodeRepository, times(1)).save(promoCode);
    }

    @Test
    void testDeletePromoCode() {
        doNothing().when(promoCodeRepository).deleteById(1L);
        promoCodeService.deletePromoCode(1L);
        verify(promoCodeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetPromoCodeByCodeSuccess() {
        when(promoCodeRepository.findByCode("DISCOUNT10")).thenReturn(Optional.of(promoCode));
        when(promoCodeMapper.toDto(promoCode)).thenReturn(promoCodeDtoResponse);

        PromoCodeDtoResponse response = promoCodeService.getPromoCodeByCode("DISCOUNT10");

        assertNotNull(response);
        assertEquals("DISCOUNT10", response.getCode());
    }

    @Test
    void testGetPromoCodeByCodeNotFound() {
        when(promoCodeRepository.findByCode("DISCOUNT10")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> promoCodeService.getPromoCodeByCode("DISCOUNT10"));
    }
}
