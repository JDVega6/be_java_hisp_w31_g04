package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.ProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.UserNotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.ProductRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepositoryImpl productRepositoryImpl;

    @Mock
    UserRepositoryImpl userRepositoryImpl;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Test
    void createPostProduct_shouldReturnSuccess() {
        //Arrange
        Product product = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");

        ProductDto productDto = new ProductDto(1,"Camisa","Ropa","Nike", "Blanca","");
        PostProductDto postProductDto = new PostProductDto(1,null, LocalDate.now(), productDto, 1,30.0);

        Optional<User> user = Optional.of(new User(1, "Test User", null, null));

        //Act
        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(user);
        Mockito.when(productRepositoryImpl.existsProduct(1)).thenReturn(false);

        Mockito.doAnswer(invocation ->
        {
            Post post = invocation.getArgument(0);
            post.setId(1);
            return null;
        }).when(productRepositoryImpl).savePost(Mockito.any(Post.class));

        productServiceImpl.createPostProduct(postProductDto);

        //Product Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productRepositoryImpl).saveProduct(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertAll("Save product successfully",
                    () -> assertEquals(1, savedProduct.getId()),
                    () -> assertEquals("Camisa",savedProduct.getName())
                );

        //Post assert
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        Mockito.verify(productRepositoryImpl).savePost(postCaptor.capture());
        Post savedPost = postCaptor.getValue();

        assertAll("Save product successfully",
                () -> assertTrue( savedPost.getId() > 0),
                () -> assertEquals(1,savedPost.getUserId()),
                ()-> assertEquals("Camisa", savedPost.getProduct().getName())
        );
    }

    @Test
    void createPostProduct_whenUserIsNotExist_shouldThrowUserNotFoundException() {
        //Arrange
        ProductDto productDto = new ProductDto(1,"Camisa","Ropa","Nike", "Blanca","");
        PostProductDto postProductDto = new PostProductDto(1,100, LocalDate.now(), productDto, 1,30.0);

        //Act
        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(Optional.empty());
        Executable executable = () -> productServiceImpl.createPostProduct(postProductDto);

        //Assert
        UserNotFoundException exception = assertThrows
                                            (
                                                    UserNotFoundException.class,
                                                    executable
                                            );

        assertEquals("No se encontró ningún usuario con ese ID", exception.getMessage());
    }

    @Test
    void createPostProduct_whenProductExist_shouldThrowBadRequestException() {
        //Arrange
        ProductDto productDto = new ProductDto(1,"Camisa","Ropa","Nike", "Blanca","");
        PostProductDto postProductDto = new PostProductDto(1,100, LocalDate.now(), productDto, 1,30.0);

        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(Optional.of(
                new User(1, "Test User", null, null
                )));
        Mockito.when(productRepositoryImpl.existsProduct(1)).thenReturn(true);

        //Act
        Executable executable = () -> productServiceImpl.createPostProduct(postProductDto);

        //Assert
        BadRequestException exception = assertThrows
                (
                        BadRequestException.class,
                        executable
                );

        assertEquals("El producto ya existe", exception.getMessage());
    }

    @Test
    void testCreatePostProduct() {
    }

    @Test
    void getFollowedPostsFromTwoWeeks() {
    }

    @Test
    void getPromoPostByUser() {
    }

    @Test
    void getPromoPostCountByUserId() {
    }

    @Test
    void getFollowedPosts() {
    }
}