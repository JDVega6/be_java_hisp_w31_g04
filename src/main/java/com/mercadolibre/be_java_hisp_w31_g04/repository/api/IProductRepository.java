package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

import java.util.List;

import java.time.LocalDate;

public interface IProductRepository {
    boolean existsProduct(Integer id);
    void savePost(Post postProduct);
    boolean saveProduct(Product product);
    int countPromoPostByUserId(Integer userId);
    List<Post> getPromoPostByUser(Integer userId);
    void deletePostByUserId(Integer userId);
    List<Post> findPostsBySellerIdsSince(List<Integer> sellerIds, LocalDate fromDate);

    Post getPostById(int id);
}
