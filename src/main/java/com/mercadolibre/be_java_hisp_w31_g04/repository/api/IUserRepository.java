package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> getById(Integer userId);
}
