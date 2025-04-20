package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;

import java.util.Optional;

public interface IProductRepository {
    Optional<Product> findProductById(int id);
    void savePost(Post postProduct);
}
