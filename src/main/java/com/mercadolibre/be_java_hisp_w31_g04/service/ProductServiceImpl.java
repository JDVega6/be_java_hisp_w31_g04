package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PostPromoProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostByUserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.PromoPostDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IProductService;
import com.mercadolibre.be_java_hisp_w31_g04.util.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {

    IProductRepository productRepositoryImpl;
    IUserRepository userRepositoryImpl;

    public ProductServiceImpl(IProductRepository productRepositoryImpl, IUserRepository userRepositoryImpl) {
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
    public void createPostProduct(PostPromoProductDto postPromoProduct) {
        userRepositoryImpl.getById(postPromoProduct.getUser_id())
                .orElseThrow(() -> new NotFoundException("No se encontró un usuario con ese ID"));

        if (postPromoProduct.getId() == 0 || postPromoProduct.getProduct().getId() == 0){
            throw new BadRequestException("Se debe ingresar el id del post y del producto");
        }

        if (!postPromoProduct.getHasPromo() || postPromoProduct.getDiscount().equals(0.0)) {
            throw  new BadRequestException("No se puede crear un post de un producto en descuento sin descuento");
        }

        Product product = ProductMapper.toProduct(postPromoProduct.getProduct());
        boolean existProduct = productRepositoryImpl.existsProduct(product.getId());
        if(existProduct){
            throw new BadRequestException("El producto ya existe");
        }

        Post post = ProductMapper.toPost(postPromoProduct,product);
        productRepositoryImpl.saveProduct(product);
        productRepositoryImpl.savePost(post);
    }

    @Override
    public PromoPostDto getPromoPostCountByUserId(int userId) {

        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        int countProductsPromo  = productRepositoryImpl.countPromoPostByUserId(userId);

        return PromoPostDto.builder()
                .userId(userId)
                .userName(user.getName())
                .promoProductsCount(countProductsPromo)
                .build();

    }

    @Override
    public PromoPostByUserDto GetPromoPostByUser(int userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));


        return PromoPostByUserDto.builder()
                .userId(userId)
                .userName(user.getName())
                .posts(productRepositoryImpl.getPromoPostByUser(userId).stream().map(ProductMapper::toPostPromoDto).toList())
                .build();

    }
}
