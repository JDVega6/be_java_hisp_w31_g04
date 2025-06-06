package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.*;

public interface IUserService {
    UserDto getUserFollowed(Integer userId, String order);

    void addFollowById(Integer userId, Integer userIdToFollow);

    FollowersCountDto getUserFollowersCount(Integer userId);

    void removeFollowById(Integer userId, Integer userIdToUnfollow);

    UserWithFollowersDto getUserWithFollowed(Integer userId, String order);

    void removeUserById(Integer userId);
    UserDto getUserById(Integer userId);
    void createUser(UserToCreateDto dtoUser);

}
