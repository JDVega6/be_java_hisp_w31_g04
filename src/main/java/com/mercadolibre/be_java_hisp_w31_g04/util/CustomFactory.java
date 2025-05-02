package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomFactory {
    public static Optional<User> getOptionalUser() {
        List<Integer> following=new ArrayList<>(List.of(3,4));
        List<Integer> followedBy=new ArrayList<>(List.of(1,10));

        return Optional.of(new User(2,"Eve",following,followedBy));
    }
    public static Optional<User> getUserThree() {
        List<Integer> following=new ArrayList<>(List.of(4,5));
        List<Integer> followedBy=new ArrayList<>(List.of(1,2,10));
        return Optional.of(new User(3,"David",following,followedBy));
    }
    public static Optional<User> getUserFour() {
        List<Integer> following=new ArrayList<>(List.of(5,6));
        List<Integer> followedBy=new ArrayList<>(List.of(2,3,10));
        return Optional.of(new User(4,"Charlie",following,followedBy));
    }
    public static UserDto getUserDtoThree() {

        return new UserDto(3,"David");
    }
    public static UserDto getUserDtoFour() {

        return new UserDto(4,"Charlie");
    }
    public static UserDto getUserDto() {
        List<UserDto> users =new ArrayList<>(List.of(getUserDtoThree(),getUserDtoFour()));
        return new UserDto(2,"Eve",users);
    }

    public static List<User> getUserList() {
        List<User> users=new ArrayList<>();
        List<Integer> following=new ArrayList<>(List.of(4,5));
        List<Integer> followedBy=new ArrayList<>(List.of(1,2,10));
        users.add(new User(3,"David",following,followedBy));
        following=new ArrayList<>(List.of(5,6));
        followedBy=new ArrayList<>(List.of(2,3,10));
        users.add(new User(4,"Charlie",following,followedBy));
        return users;

    }
    public static List<User>getUserListAsc() {
        List<User> users=new ArrayList<>();
        List<Integer> following=new ArrayList<>(List.of(5,6));
        List<Integer> followedBy=new ArrayList<>(List.of(2,3,10));
        users.add(new User(4,"Charlie",following,followedBy));
        following=new ArrayList<>(List.of(4,5));
        followedBy=new ArrayList<>(List.of(1,2,10));
        users.add(new User(3,"David",following,followedBy));
        return users;

    }
}
