package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostByUserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.ProductServiceImpl;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> PostPostProduct(@Validated @RequestBody PostProductDto PostProduct) {
        productService.createPostProduct(PostProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created: Post del producto creado exitosamente.");
    }

    @GetMapping("/promo-post/count")
    public ResponseEntity<PromoPostDto> GetPromoPostCount(@RequestParam Integer user_id) {
        return new ResponseEntity<PromoPostDto>(productService.getPromoPostCountByUserId(user_id), HttpStatus.OK);
    }

    @GetMapping("/promo-post/list")
    public ResponseEntity<PromoPostByUserDto> GetPromoPostByUser(@RequestParam Integer user_id) {
        return new ResponseEntity<PromoPostByUserDto>(productService.GetPromoPostByUser(user_id), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<FollowedPostsResponseDto> getFollowePosts(@PathVariable int userId){
        return new ResponseEntity<FollowedPostsResponseDto>(productService.getFollowedPostsResponse(userId), HttpStatus.OK);
    }
}
