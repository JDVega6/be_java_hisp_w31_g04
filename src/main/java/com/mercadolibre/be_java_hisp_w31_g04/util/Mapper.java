package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

public class Mapper {
    public static UserFollowedDto toUserFollowedDto(User user) {
        return new UserFollowedDto(user.getId(), user.getName());
    }
}
