package org.mustapha.smartShop.controller;

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
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    @PostMapping
    public ResponseEntity<PromoCodeDtoResponse> create(@RequestBody PromoCodeDtoRequest dto) {
        return ResponseEntity.ok(promoCodeService.createPromoCode(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromoCodeDtoResponse>> getAll() {
        return ResponseEntity.ok(promoCodeService.getAllPromoCodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeDtoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(promoCodeService.getPromoCodeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeDtoResponse> update(@PathVariable Long id, @RequestBody PromoCodeDtoRequest dto) {
        return ResponseEntity.ok(promoCodeService.updatePromoCode(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promoCodeService.deletePromoCode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<PromoCodeDtoResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(promoCodeService.getPromoCodeByCode(code));
    }
}
