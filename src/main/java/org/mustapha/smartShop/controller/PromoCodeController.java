package org.mustapha.smartShop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mustapha.smartShop.dto.request.PromoCodeDtoRequest;
import org.mustapha.smartShop.dto.response.PromoCodeDtoResponse;
import org.mustapha.smartShop.service.PromoCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/promocodes")
@RequiredArgsConstructor
@Tag(name = "Promo Codes", description = "Manage promo codes")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @PostMapping
    @Operation(summary = "Create new promo code", description = "Add a new promo code to the system")
    public ResponseEntity<PromoCodeDtoResponse> create(@RequestBody PromoCodeDtoRequest dto) {
        return ResponseEntity.ok(promoCodeService.createPromoCode(dto));
    }

    @GetMapping
    @Operation(summary = "Get all promo codes", description = "List all promo codes")
    public ResponseEntity<List<PromoCodeDtoResponse>> getAll() {
        return ResponseEntity.ok(promoCodeService.getAllPromoCodes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get promo code by ID")
    public ResponseEntity<PromoCodeDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(promoCodeService.getPromoCodeById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update promo code")
    public ResponseEntity<PromoCodeDtoResponse> update(@PathVariable Long id, @RequestBody PromoCodeDtoRequest dto) {
        return ResponseEntity.ok(promoCodeService.updatePromoCode(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete promo code")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get promo code by code value")
    public ResponseEntity<PromoCodeDtoResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(promoCodeService.getPromoCodeByCode(code));
    }
}
