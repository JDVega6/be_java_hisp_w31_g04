package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;

public interface IUserService {
    UserFollowedDto getUserFollowed(Integer userId, String order);
}
