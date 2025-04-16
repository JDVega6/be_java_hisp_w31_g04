package com.mercadolibre.be_java_hisp_w31_g04.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserFollowedDto {
    private int user_id;
    private String user_name;
    private List<UserFollowedDto> followed;

    public UserFollowedDto(int id, String name) {
        this.user_id = id;
        this.user_name = name;
        followed = null;
    }
}

