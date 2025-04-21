package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private List<Product> listOfProducts = new ArrayList<>();
    private List<Post> listOfPosts = new ArrayList<>();

    @Override
    public boolean existsProduct(int id) {
        return listOfProducts.stream().anyMatch(product -> product.getId() == id);
    }

    @Override
    public void savePost(Post postProduct) {
        listOfPosts.add(postProduct);
    }

    @Override
    public void saveProduct(Product product) {
        listOfProducts.add(product);
    }

    @Override
    public int countPromoPostByUserId(int userId) {

        int countProductsPromo = Math.toIntExact( listOfPosts.stream()
                                .filter(post -> post.getUserId() == userId)
                                .filter(Post::getHasPromo)
                                .count());

        return countProductsPromo;
    }

}
