package com.mercadolibre.be_java_hisp_w31_g04.repository.api;

import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> getById(Integer userId);

    void deleteFollowById(User user, User toUnfollow);
    void addFollowById(Integer userId, Integer userIdToFollow);
    void orderUsers(List<User> user, String order);
    void saveUser(User user);
    int getUserId();

    void deleteUserById(Integer userId);
}
