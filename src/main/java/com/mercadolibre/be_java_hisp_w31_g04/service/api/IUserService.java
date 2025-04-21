package com.mercadolibre.be_java_hisp_w31_g04.service.api;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;

public interface IUserService {
    UserFollowedDto getUserFollowed(Integer userId,String order);

    FollowersCountDto getUserFollowersCount(Integer userId);
    UserWithFollowersDto getUserWithFollowed(Integer userId);
}
