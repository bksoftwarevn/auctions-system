package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.BrandsResponse;
import com.bksoftwarevn.auction.model.CreateBrandRequest;
import com.bksoftwarevn.auction.model.CreateBrandResponse;
import com.bksoftwarevn.auction.model.UpdateBrandRequest;
import com.bksoftwarevn.auction.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class BrandController implements com.bksoftwarevn.auction.api.v1.BrandApi {

    private final BrandService brandService;


    @Override
    public ResponseEntity<BrandsResponse> getBrands() {
        log.info("[BrandController.getBrands] Start get list brand");
        return ResponseEntity.ok(brandService.pull());
    }

    @Override
    public ResponseEntity<CreateBrandResponse> postCreateBrand(CreateBrandRequest createBrandRequest) {
        log.info("[BrandController.postCreateBrand] Start create brand: {}", createBrandRequest);
        return ResponseEntity.ok(brandService.create(createBrandRequest));
    }

    @Override
    public ResponseEntity<CreateBrandResponse> putUpdateBrand(UpdateBrandRequest updateBrandRequest) {
        log.info("[BrandController.putUpdateBrand] Start update brand: {}", updateBrandRequest);
        return ResponseEntity.ok(brandService.update(updateBrandRequest));
    }
}
