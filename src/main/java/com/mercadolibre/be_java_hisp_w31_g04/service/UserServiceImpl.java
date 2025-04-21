package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
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
        this.orderUsers(followed,order);


        return new UserDto(user.getId(), user.getName(), followed);
    }

    @Override
    public void addFollowById(Integer userId, Integer userIdToFollow) {

        if(userId.equals(userIdToFollow)){
            throw new BadRequestException("No es posible generar esta acción");
        }

        User user = userRepositoryImpl.getById(userId).orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        User toFollow = userRepositoryImpl.getById(userIdToFollow).orElseThrow(()-> new NotFoundException("Usuario a seguir no encontrado"));

        if(user.getFollowing().contains(userIdToFollow)){
            throw new BadRequestException("Ya sigues a este usuario");
        }

        userRepositoryImpl.addFollowById(userId, userIdToFollow);
    }

    @Override
    public FollowersCountDto getUserFollowersCount(Integer userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        return UserMapper.toFollowersCountDto(user, user.getFollowedBy().size());
    }

    @Override
    public UserWithFollowersDto getUserWithFollowed(Integer userId, String order) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró nungun usuario"));

        List<UserDto> followedBy = new ArrayList<>(user.getFollowedBy().stream()
                .map(u -> userRepositoryImpl.getById(u).get())
                .map(UserMapper::toUserDto)
                .toList());

        this.orderUsers(followedBy,order);



        return UserMapper.toUserWithFollowersDto(user, followedBy);
    }

    @Override
    public void orderUsers(List<UserDto> user,String order) {
        if(order.equals("name_asc")) {
            user.sort(new Comparator<UserDto>() {
                public int compare(UserDto obj1, UserDto obj2) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }
        if(order.equals("name_desc")) {
            user.sort(new Comparator<UserDto>() {
                public int compare(UserDto obj2, UserDto obj1) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }

    }


}
