package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IProductRepository;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import com.mercadolibre.be_java_hisp_w31_g04.util.UserMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    IUserRepository userRepositoryImpl;
    IProductRepository productRepositoryImpl;

    private static final String USER_NOT_FOUND_MESSAGE = "No se encontró ningún usuario con ese ID";

    public UserServiceImpl(IUserRepository userRepositoryImpl, IProductRepository productRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
        this.productRepositoryImpl = productRepositoryImpl;
    }

    @Override
    public void createUser(UserToCreateDto dtoUser) {
        if(dtoUser.getName().length()>=20){
            throw new BadRequestException("el nombre no puede tener mas de 20 caracteres");
        }else if(dtoUser.getName().isEmpty()){
            throw new BadRequestException("el nombre no puede estar vacio");
        }
        userRepositoryImpl.saveUser(new User(userRepositoryImpl.getUserId(),dtoUser.getName(),new ArrayList<>(),new ArrayList<>()));
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user =  userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserFollowed(Integer userId, String order) {
        //mio
        User user= userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

        List<UserDto> followed=new ArrayList<>();
        List<User> users= new ArrayList<>(user.getFollowing().stream().map(u->userRepositoryImpl.getById(u).get()).toList());
        userRepositoryImpl.orderUsers(users, order);
        users.forEach(user1-> followed.add(UserMapper.toUserDto(user1)) );



        return new UserDto(user.getId(), user.getName(), followed);
    }

    @Override
    public UserWithFollowersDto getUserWithFollowed(Integer userId, String order) {

        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        List<User> followedBy = new ArrayList<>(user.getFollowedBy().stream()
                .map(u -> userRepositoryImpl.getById(u).get()).toList());
        userRepositoryImpl.orderUsers(followedBy, order);
        List<UserDto>followedByDto=followedBy.stream().map(UserMapper::toUserDto).toList();
        return UserMapper.toUserWithFollowersDto(user, followedByDto);
    }

    @Override
    public FollowersCountDto getUserFollowersCount(Integer userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

        return UserMapper.toFollowersCountDto(user, user.getFollowedBy().size());
    }

    @Override
    public UserWithFollowersDto removeFollow(Integer userId, Integer userIdToUnfollow) {
        if (userId.equals(userIdToUnfollow)){
            throw new BadRequestException("No es posible realizar esta acción");
        }

        User user = userRepositoryImpl.getById(userId).
                orElseThrow(()-> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        User toUnfollow = userRepositoryImpl.getById(userIdToUnfollow)
                .orElseThrow(()-> new NotFoundException(USER_NOT_FOUND_MESSAGE));

        if (!user.getFollowing().contains(userIdToUnfollow)) {
            throw new BadRequestException("No sigues a este usuario");
        }

        User updatedUser = userRepositoryImpl.removeFromFollowing(user, toUnfollow);
        userRepositoryImpl.removeFromFollowedBy(toUnfollow, user);

        List<User> updatedFollowing = new ArrayList<>(user.getFollowing().stream()
                .map(u -> userRepositoryImpl.getById(u).get()).toList());
        List<UserDto>updatedFollowingDto = updatedFollowing.stream().map(UserMapper::toUserDto).toList();

        return UserMapper.toUserWithFollowersDto(updatedUser, updatedFollowingDto);
    }

    @Override
    public void removeUserById(Integer userId) {
        userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));

        productRepositoryImpl.deletePostByUserId(userId);
        userRepositoryImpl.deleteUserById(userId);

    }

    @Override
    public UserWithFollowersDto updateFollowByUserId(Integer userId, Integer userIdToFollow) {

        if(userId.equals(userIdToFollow)){
            throw new BadRequestException("No es posible generar esta acción");
        }

        User user = userRepositoryImpl.getById(userId).orElseThrow(()-> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        User toFollowedBy = userRepositoryImpl.getById(userIdToFollow).orElseThrow(()-> new NotFoundException("El usuario a seguir no existe"));

        if(user.getFollowing().contains(userIdToFollow)){
            throw new BadRequestException("Ya sigues a este usuario");
        }

        User followingUpdated = userRepositoryImpl.updateFollowByUserId(user, toFollowedBy);

        List<UserDto> followers = followingUpdated.getFollowing().stream()
                .map(f -> userRepositoryImpl.getById(f)
                        .map(UserMapper::toUserDto)
                        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE)))
                .toList();

        return UserMapper.toUserWithFollowersDto(followingUpdated, followers);
    }


}
