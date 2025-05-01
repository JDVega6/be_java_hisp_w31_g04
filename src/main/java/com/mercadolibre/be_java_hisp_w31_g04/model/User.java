package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private List<Integer>following;
    private List<Integer> followedBy;
}
