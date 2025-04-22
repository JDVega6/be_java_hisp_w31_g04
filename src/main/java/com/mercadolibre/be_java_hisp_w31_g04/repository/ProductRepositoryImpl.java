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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private List<Product> listOfProducts = new ArrayList<>();
    private List<Post> listOfPosts = new ArrayList<>();

    public ProductRepositoryImpl() throws IOException{
        loadDataPosts();
    }

    //Esta fallando con error de controller ??
    public void loadDataPosts() throws IOException{
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> posts;

        file= ResourceUtils.getFile("classpath:posts.json");
        posts= objectMapper.readValue(file,new TypeReference<List<Post>>(){});

        listOfPosts = posts;
    }

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
    public List<Post> findPostsBySellerIdsSince(List<Integer> sellerIds, LocalDate fromDate) {
        return listOfPosts.stream()
                .filter(post -> sellerIds.contains(post.getUserId()))
                .filter(post -> !post.getDate().isBefore(fromDate))
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }
}
