package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

import java.time.LocalDate;
import java.util.List;

public interface IProductRepository {
    boolean existsProduct(int id);
    void savePost(Post postProduct);
    void saveProduct(Product product);
    List<Post> findPostsBySellerIdsSince(List<Integer> sellerIds, LocalDate fromDate);
}
