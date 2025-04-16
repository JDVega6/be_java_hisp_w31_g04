package com.mercadolibre.be_java_hisp_w31_g04.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowersCountDto {
    private int user_id;
    private String user_name;
    private int followers_count;
}
