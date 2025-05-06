package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {

    IProductRepository productRepository;

    @BeforeEach
    void setUp() throws IOException {
        productRepository = new ProductRepositoryImpl();
    }

    @Test
    void savePost_shouldSavePostSuccessfully() {
        //Arrange
        Product product = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");
        Post post = new Post(1,null, LocalDate.now(),product,1, 10.0, false, 0.0);

        //Act
        productRepository.savePost(post);

        //Assert
        assertAll("SavePost succesfully",
                    () -> assertNotNull(post.getId()),
                    () -> assertTrue(post.getId() > 0)
                );
    }

    @Test
    void saveProduct_shouldSaveProductSuccessfully() {
        //Arrange
        Product product = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");

        //act
        boolean success = productRepository.saveProduct(product);

        //Assert
        assertTrue(success);
    }

    @Test
    void existsProduct_whenProductExists_shouldReturnTrue() {
        // Arrange
        Product existingProduct = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");
        productRepository.saveProduct(existingProduct);

        // Act
        boolean exists = productRepository.existsProduct(1);

        // Assert
        assertTrue(exists, "El producto con ID 1 debería existir");
    }

    @Test
    void existsProduct_shouldReturnFalse_whenProductDoesNotExist() {
        // Arrange
        int productId = 99;

        // Act:
        boolean exists = productRepository.existsProduct(productId);

        // Assert
        assertFalse(exists, "El producto con ID 99 no debería existir");
    }

    @Test

    void countPromoPostByUserId() {
    }

    @Test
    void getPromoPostByUser() {
    }

    @Test
    void findPostsBySellerIdsSince() {
    }

    @Test
    void getPostById() {
    }

    @Test
    void deletePostByUserId() {
    }
}