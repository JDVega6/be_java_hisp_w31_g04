package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IProductService;
import com.mercadolibre.be_java_hisp_w31_g04.util.ProductMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final UserRepositoryImpl userRepositoryImpl;
    IProductRepository productRepositoryImpl;
    IUserRepository userRepositoryImp;

    public ProductServiceImpl(IProductRepository productRepositoryImpl, UserRepositoryImpl userRepositoryImpl){
        this.productRepositoryImpl = productRepositoryImpl;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    @Override
    public void createPostProduct(PostProductDto postProduct) {
        if (postProduct.getId() == 0 || postProduct.getProduct().getId() == 0){
            throw new BadRequestException("Se debe ingresar el id del post y del producto");
        }

        Product product = ProductMapper.toProduct(postProduct.getProduct());
        boolean existProduct = productRepositoryImpl.existsProduct(product.getId());
        if(existProduct){
            throw new BadRequestException("El producto ya existe");
        }

        Post post = ProductMapper.toPost(postProduct,product);
        productRepositoryImpl.saveProduct(product);
        productRepositoryImpl.savePost(post);
    }

    @Override
    public List<PostProductDto> getFollowedPosts(int userId){
        if (userId == 0 || userId < 0 ){
            throw new BadRequestException("Debe ingresar un id valido");
        }

        User user = userRepositoryImp.getById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        List<Integer> sellerIds = user.getFollowing();
        if (sellerIds.isEmpty()) {
            return List.of();
        }

        LocalDate fromDate = LocalDate.now().minusWeeks(2);

        List<Post> posts = productRepositoryImpl.findPostsBySellerIdsSince(sellerIds,fromDate);

        return posts.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
