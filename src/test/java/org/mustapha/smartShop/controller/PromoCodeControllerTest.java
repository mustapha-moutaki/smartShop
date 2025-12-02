package org.mustapha.smartShop.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;
import org.mustapha.smartShop.service.PromoCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class PromoCodeControllerTest {

    @Mock
    private PromoCodeService promoCodeService;

    @InjectMocks
    private PromoCodeController promoCodeController;

    private PromoCodeDtoRequest promoCodeDtoRequest;
    private PromoCodeDtoResponse promoCodeDtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize request
        promoCodeDtoRequest = new PromoCodeDtoRequest();
        promoCodeDtoRequest.setCode("DISCOUNT10");
        promoCodeDtoRequest.setPercentage(10.0);
        promoCodeDtoRequest.setActive(true);

        // Initialize response
        promoCodeDtoResponse = new PromoCodeDtoResponse();
        promoCodeDtoResponse.setId(1L);
        promoCodeDtoResponse.setCode("DISCOUNT10");
        promoCodeDtoResponse.setPercentage(10.0);
        promoCodeDtoResponse.setActive(true);
    }

    @Test
    void testCreatePromoCode() {
        when(promoCodeService.createPromoCode(promoCodeDtoRequest)).thenReturn(promoCodeDtoResponse);

        ResponseEntity<PromoCodeDtoResponse> response = promoCodeController.create(promoCodeDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DISCOUNT10", response.getBody().getCode());
        verify(promoCodeService, times(1)).createPromoCode(promoCodeDtoRequest);
    }

    @Test
    void testGetAllPromoCodes() {
        List<PromoCodeDtoResponse> promoList = new ArrayList<>();
        promoList.add(promoCodeDtoResponse);

        when(promoCodeService.getAllPromoCodes()).thenReturn(promoList);

        ResponseEntity<List<PromoCodeDtoResponse>> response = promoCodeController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(promoCodeService, times(1)).getAllPromoCodes();
    }

    @Test
    void testGetById() {
        when(promoCodeService.getPromoCodeById(1L)).thenReturn(promoCodeDtoResponse);

        ResponseEntity<PromoCodeDtoResponse> response = promoCodeController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(promoCodeService, times(1)).getPromoCodeById(1L);
    }

    @Test
    void testUpdatePromoCode() {
        when(promoCodeService.updatePromoCode(1L, promoCodeDtoRequest)).thenReturn(promoCodeDtoResponse);

        ResponseEntity<PromoCodeDtoResponse> response = promoCodeController.update(1L, promoCodeDtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DISCOUNT10", response.getBody().getCode());
        verify(promoCodeService, times(1)).updatePromoCode(1L, promoCodeDtoRequest);
    }

    @Test
    void testDeletePromoCode() {
        doNothing().when(promoCodeService).deletePromoCode(1L);

        ResponseEntity<Void> response = promoCodeController.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(promoCodeService, times(1)).deletePromoCode(1L);
    }

    @Test
    void testGetByCode() {
        when(promoCodeService.getPromoCodeByCode("DISCOUNT10")).thenReturn(promoCodeDtoResponse);

        ResponseEntity<PromoCodeDtoResponse> response = promoCodeController.getByCode("DISCOUNT10");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DISCOUNT10", response.getBody().getCode());
        verify(promoCodeService, times(1)).getPromoCodeByCode("DISCOUNT10");
    }
}
