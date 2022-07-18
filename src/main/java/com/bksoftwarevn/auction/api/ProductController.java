package com.bksoftwarevn.auction.api;

import com.bksoftwarevn.auction.model.CreateProductRequest;
import com.bksoftwarevn.auction.model.CreateProductResponse;
import com.bksoftwarevn.auction.model.UpdateProductRequest;
import com.bksoftwarevn.auction.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController implements com.bksoftwarevn.auction.api.v1.ProductApi {

    private final ProductService productService;


    @Override
    public ResponseEntity<CreateProductResponse> getProduct(String id) {
        log.info("[ProductController.getProduct] Start get product: [{}]", id);
        return ResponseEntity.ok(productService.detail(id));
    }

    @Override
    public ResponseEntity<CreateProductResponse> postCreateProduct(CreateProductRequest createProductRequest) {
        log.info("[ProductController.postCreateProduct] Start create product: [{}]", createProductRequest);
        return ResponseEntity.ok(productService.create(createProductRequest));
    }

    @Override
    public ResponseEntity<CreateProductResponse> putUpdateProduct(UpdateProductRequest updateProductRequest) {
        log.info("[ProductController.putUpdateProduct] Start update product: [{}]", updateProductRequest);
        return ResponseEntity.ok(productService.update(updateProductRequest));
    }
}
