package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;

public interface IUserService {
    UserFollowedDto getUserFollowed(Integer userId);

    void addFollowById(Integer userId, Integer userIdToFollow);

    FollowersCountDto getUserFollowersCount(Integer userId);
}
