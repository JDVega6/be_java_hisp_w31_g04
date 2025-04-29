package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private List<Product> listOfProducts = new ArrayList<>();
    private List<Post> listOfPosts = new ArrayList<>();

    public ProductRepositoryImpl(){
        setListOfPostsMannually();
    }

    public void setListOfPostsMannually(){
        Product p1 = Product.builder()
                .id(2)
                .name("Pc Gamer")
                .type("Game")
                .brand("HP")
                .color("Blue")
                .notes("NA")
                .build();

        listOfPosts.add(Post.builder()
                .userId(2)
                .id(1)
                .date(LocalDate.of(2025,5,1))
                .product(p1)
                .category(100)
                .price(25.37)
                .hasPromo(true)
                .discount(63.9)
                .build());

        Product p2 = Product.builder()
                .id(2)
                .name("Pc Gamer")
                .type("Game")
                .brand("HP")
                .color("Blue")
                .notes("NA")
                .build();

        listOfPosts.add(Post.builder()
                .userId(2)
                .id(2)
                .date(LocalDate.of(2025,5,2))
                .product(p2)
                .category(100)
                .price(25.37)
                .hasPromo(true)
                .discount(63.9)
                .build());

        Product p3 = Product.builder()
                .id(2)
                .name("Pc Gamer")
                .type("Game")
                .brand("HP")
                .color("Blue")
                .notes("NA")
                .build();

        listOfPosts.add(Post.builder()
                .userId(2)
                .id(3)
                .date(LocalDate.of(2025,5,3))
                .product(p3)
                .category(100)
                .price(25.37)
                .hasPromo(true)
                .discount(63.9)
                .build());
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
    public boolean existsProduct(int id) {
        return listOfProducts.stream().anyMatch(product -> product.getId() == id);
    }

    @Override
    public int countPromoPostByUserId(int userId) {

        int countProductsPromo = Math.toIntExact( listOfPosts.stream()
                                .filter(post -> post.getUserId() == userId)
                                .filter(Post::getHasPromo)
                                .count());

        return countProductsPromo;
    }

    @Override
    public List<Post> getPromoPostByUser(int userId) {
        return listOfPosts.stream().filter(post -> post.getUserId() == userId && post.getHasPromo()).toList();
    }
    @Override
    public List<Post> findPostsBySellerIdsSince(List<Integer> sellerIds, LocalDate fromDate) {


        return listOfPosts.stream()
                .filter(post -> sellerIds.contains(post.getUserId()))
                .filter(post -> !post.getDate().isBefore(fromDate))
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void deletePostByUserId(int userId) {
        listOfPosts.stream().filter(post -> post.getUserId() == userId)
                            .forEach(post -> listOfProducts.remove(post.getProduct()));

        listOfPosts.removeIf(post -> post.getUserId() == userId);
    }
}



