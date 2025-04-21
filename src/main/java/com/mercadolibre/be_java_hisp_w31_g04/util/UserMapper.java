package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

import java.util.List;

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

    public static UserWithFollowersDto toUserWithFollowedDto(User user, List<UserFollowedDto> followed) {
        return new UserWithFollowersDto(
                user.getId(),
                user.getName(),
                followed
        );
    }
}
