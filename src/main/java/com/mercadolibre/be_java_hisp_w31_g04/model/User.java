package com.mercadolibre.be_java_hisp_w31_g04.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private int id;
    private String name;
    private List<Integer>following;
    private List<Integer> followedBy;
}
