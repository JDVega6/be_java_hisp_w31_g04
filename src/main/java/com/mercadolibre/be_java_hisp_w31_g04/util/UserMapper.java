package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

public class UserMapper {
    public static UserFollowedDto toUserFollowedDto(User user) {
        return new UserFollowedDto(user.getId(), user.getName());
    }

    public static FollowersCountDto toFollowersCountDto(User user, int followers) {
        return new FollowersCountDto(
                user.getId(),
                user.getName(),
                followers
        );
    }
}
