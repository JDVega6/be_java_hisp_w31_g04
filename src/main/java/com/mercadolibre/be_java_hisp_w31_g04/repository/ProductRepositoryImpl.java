package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private List<Product> listOfProducts;
    private List<Post> listOfPosts = new ArrayList<>();

    private final AtomicInteger postIdCounter = new AtomicInteger(1);

    public ProductRepositoryImpl() throws IOException {
        loadDataBase();
    }

    @Override
    public Optional<Product> findProductById(int id) {
        return listOfProducts.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public void savePost(Post postProduct) {
        postProduct.setId(postIdCounter.getAndIncrement());
        listOfPosts.add(postProduct);
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products ;

        file= ResourceUtils.getFile("classpath:products.json");
        products= objectMapper.readValue(file,new TypeReference<List<Product>>(){});

        listOfProducts = products;
    }
}
