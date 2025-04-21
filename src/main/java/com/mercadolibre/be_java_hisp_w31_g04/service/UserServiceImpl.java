package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import com.mercadolibre.be_java_hisp_w31_g04.util.UserMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {


    IUserRepository userRepositoryImpl;

    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl){this.userRepositoryImpl = userRepositoryImpl;}
    @Override
    public UserDto getUserFollowed(Integer userId, String order) {
        User user= userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        List<UserDto> followed=new ArrayList<>();
        List<User> users= user.getFollowing().stream().map(u->userRepositoryImpl.getById(u).get()).toList();
        users.forEach(user1->{followed.add(UserMapper.toUserDto(user1));});
        if(order.equals("name_asc")) {
            followed.sort(new Comparator<UserDto>() {
                public int compare(UserDto obj1, UserDto obj2) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }
        if(order.equals("name_desc")) {
            followed.sort(new Comparator<UserDto>() {
                public int compare(UserDto obj2, UserDto obj1) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }




        return new UserDto(user.getId(), user.getName(), followed);
    }

    @Override
    public FollowersCountDto getUserFollowersCount(Integer userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        return UserMapper.toFollowersCountDto(user, user.getFollowedBy().size());
    }

    @Override
    public UserWithFollowersDto getUserWithFollowed(Integer userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró nungun usuario"));

        List<UserDto> followedBy = user.getFollowedBy().stream()
                                    .map(u-> userRepositoryImpl.getById(u).get())
                                    .map(UserMapper::toUserDto)
                                    .toList();

        return UserMapper.toUserWithFollowersDto(user, followedBy);
    }
}
