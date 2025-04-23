package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowedPostsResponseDto;
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

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        userRepositoryImpl.getById(postProduct.getUser_id())
                .orElseThrow(() -> new NotFoundException("No se encontró un usuario con ese ID"));

        if (postProduct.getId() == 0 || postProduct.getProduct().getId() == 0){
            throw new BadRequestException("Se debe ingresar el id del post y del producto");
        }

        if (productRepositoryImpl.getPostById(postProduct.getId()) != null) {
            throw new BadRequestException("El post ya fue creado");
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

        if (productRepositoryImpl.getPostById(postPromoProduct.getId()) != null) {
            throw new BadRequestException("El post ya fue creado");
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

    @Override
    public List<PostProductDto> getFollowedPosts(int userId){
        if ( userId <= 0 ){
            throw new BadRequestException("Debe ingresar un id valido");
        }

        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        List<Integer> sellerIds = user.getFollowing();
        if (sellerIds.isEmpty()) {
            throw new BadRequestException("El usuario seleccionado no posee ningun vendedor con post recientes");
        }

        LocalDate fromDate = LocalDate.now().minusWeeks(2);

        List<Post> posts = productRepositoryImpl.findPostsBySellerIdsSince(sellerIds,fromDate);

        return posts.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public FollowedPostsResponseDto getFollowedPostsResponse(int userId, String order) {
        List<PostProductDto> posts = getFollowedPosts(userId);

        if(!order.isEmpty())
        {
            switch (order) {
                case "date_asc":
                    posts.sort(Comparator.comparing(PostProductDto::getDate));
                    break;
                case "date_desc":
                    posts.sort(Comparator.comparing(PostProductDto::getDate).reversed());
                    break;
                default:
                    throw new BadRequestException("Parámetro 'order' inválido. Usa 'date_asc' o 'date_desc'.");
            }
        }

        FollowedPostsResponseDto response = new FollowedPostsResponseDto();
        response.setUserId(userId);
        response.setPosts(posts);

        return response;
    }
}
