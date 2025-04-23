package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

import java.util.List;

import java.time.LocalDate;

public interface IProductRepository {
    boolean existsProduct(int id);
    void savePost(Post postProduct);
    void saveProduct(Product product);
    int countPromoPostByUserId(int userId);
    List<Post> getPromoPostByUser(int userId);
    void deletePostByUserId(int userId);
    List<Post> findPostsBySellerIdsSince(List<Integer> sellerIds, LocalDate fromDate);
}
