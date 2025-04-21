package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.PostProductDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.Post;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IProductService;
import com.mercadolibre.be_java_hisp_w31_g04.util.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    IProductRepository productRepositoryImpl;

    public ProductServiceImpl(IProductRepository productRepositoryImpl){
        this.productRepositoryImpl = productRepositoryImpl;
    }

    @Override
    public void createPostProduct(PostProductDto postProduct) {
        Optional<Product> product = productRepositoryImpl.findProductById(postProduct.getProduct_id());

        if(product.isEmpty()){
            throw new NotFoundException("Product not found");
        }

        Post post = ProductMapper.toPost(postProduct, product.get());

        productRepositoryImpl.savePost(post);
    }
}
