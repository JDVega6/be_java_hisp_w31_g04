package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

import java.util.List;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }

    public static FollowersCountDto toFollowersCountDto(User user, int followers) {
        return new FollowersCountDto(
                user.getId(),
                user.getName(),
                followers
        );
    }

    public static UserWithFollowersDto toUserWithFollowersDto(User user, List<UserDto> followed) {
        return new UserWithFollowersDto(
                user.getId(),
                user.getName(),
                followed
        );
    }
}
