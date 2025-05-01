package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostPromoProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostByUserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> postPostProduct(@Valid @RequestBody PostProductDto postProduct) {
        productService.createPostProduct(postProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post del producto creado exitosamente.");
    }

    @PostMapping("/promo-post")
    public ResponseEntity<String> createPostPromoProduct(@Valid @RequestBody PostPromoProductDto postPromoProductDto) {
        productService.createPostProduct(postPromoProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post del producto en promoción creado exitosamente.");
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<FollowedPostsResponseDto> getFollowePosts(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "") String order){
        return new ResponseEntity<>(productService.getFollowedPostsResponse(userId, order), HttpStatus.OK);
    }

    @GetMapping("/promo-post/list")
    public ResponseEntity<PromoPostByUserDto> getPromoPostByUser(@RequestParam(name = "user_id") Integer userId) {
        return new ResponseEntity<>(productService.getPromoPostByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/promo-post/count")
    public ResponseEntity<PromoPostDto> getPromoPostCount(@RequestParam(name = "user_id") Integer userId) {
        return new ResponseEntity<>(productService.getPromoPostCountByUserId(userId), HttpStatus.OK);
    }

}
