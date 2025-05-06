package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.ProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.UserNotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.ProductRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        Optional<User> user = Optional.of(new User(1, "Test User", null, null));

        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(
                    user
                );
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
    void getFollowedPostsFromTwoWeeks_shouldReturnListOfPostsSortedByDateAscending() {
        // Arrange
        Integer userId = 1;
        String order = "date_asc";

        ProductDto productDto = new ProductDto(1, "Camisa", "Ropa", "Nike", "Blanca", "");
        Product product = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");

        Optional<User> user1 = CustomFactory.getUserOne();     // userId = 1, sigue a [2]
        Optional<User> user2 = CustomFactory.getOptionalUser(); // userId = 2

        Post post1 = new Post(1, 1, LocalDate.now().minusDays(5), product, 1, 30.0,false,0.0);
        Post post2 = new Post(1, 2, LocalDate.now().minusDays(1), product, 1, 20.0,false,0.0);
        Post post3 = new Post(1, 3, LocalDate.now().minusDays(3), product, 1, 30.0, false,0.0);

        List<Post> unorderedPosts = Arrays.asList(post1, post2, post3);

        // Act
        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(user1);

        Mockito.when(productRepositoryImpl.findPostsBySellerIdsSince(
                        Mockito.anyList(), Mockito.any(LocalDate.class)))
                .thenReturn(unorderedPosts);

        FollowedPostsResponseDto response = productServiceImpl.getFollowedPostsFromTwoWeeks(userId, order);

        // Assert
        List<PostProductDto> resultPosts = response.getPosts();

        assertAll("Verifica orden ascendente por fecha",
                () -> assertEquals(3, resultPosts.size()),
                () -> assertTrue(
                        resultPosts.getFirst().getDate().isBefore(resultPosts.get(1).getDate()) ||
                                 resultPosts.getFirst().getDate().isEqual(resultPosts.get(1).getDate())),
                () -> assertTrue(
                        resultPosts.get(1).getDate().isBefore(resultPosts.get(2).getDate()) ||
                                 resultPosts.get(1).getDate().isEqual(resultPosts.get(2).getDate()))
        );
    }

    @Test
    void getFollowedPostsFromTwoWeeks_shouldReturnListOfPostsSortedByDateDescending() {
        // Arrange
        Integer userId = 1;
        String order = "date_desc";

        ProductDto productDto = new ProductDto(1, "Camisa", "Ropa", "Nike", "Blanca", "");
        Product product = new Product(1, "Camisa", "Ropa", "Nike", "Blanca", "");

        Optional<User> user1 = CustomFactory.getUserOne();     // userId = 1, sigue a [2]
        Optional<User> user2 = CustomFactory.getOptionalUser(); // userId = 2

        Post post1 = new Post(1, 1, LocalDate.now().minusDays(5), product, 1, 30.0,false,0.0);
        Post post2 = new Post(1, 2, LocalDate.now().minusDays(1), product, 1, 20.0,false,0.0);
        Post post3 = new Post(1, 3, LocalDate.now().minusDays(3), product, 1, 30.0, false,0.0);

        List<Post> unorderedPosts = Arrays.asList(post1, post2, post3);

        // Act
        Mockito.when(userRepositoryImpl.getById(1)).thenReturn(user1);

        Mockito.when(productRepositoryImpl.findPostsBySellerIdsSince(
                        Mockito.anyList(), Mockito.any(LocalDate.class)))
                .thenReturn(unorderedPosts);

        FollowedPostsResponseDto response = productServiceImpl.getFollowedPostsFromTwoWeeks(userId, order);

        // Assert
        List<PostProductDto> resultPosts = response.getPosts();

        assertAll("Verifica orden ascendente por fecha",
                () -> assertEquals(3, resultPosts.size()),
                () -> assertTrue(
                        resultPosts.getFirst().getDate().isAfter(resultPosts.get(1).getDate()) ||
                                resultPosts.getFirst().getDate().isEqual(resultPosts.get(1).getDate())),
                () -> assertTrue(
                        resultPosts.get(1).getDate().isAfter(resultPosts.get(2).getDate()) ||
                                resultPosts.get(1).getDate().isEqual(resultPosts.get(2).getDate()))
        );
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