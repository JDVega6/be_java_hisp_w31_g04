package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import com.mercadolibre.be_java_hisp_w31_g04.util.UserMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {


    IUserRepository userRepositoryImpl;

    public UserServiceImpl(UserRepositoryImpl userRepositoryImpl){this.userRepositoryImpl = userRepositoryImpl;}
    @Override
    public UserFollowedDto getUserFollowed(Integer userId) {
        User user= userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        List<UserFollowedDto> followed=new ArrayList<>();
        List<User> users= user.getFollowing().stream().map(u->userRepositoryImpl.getById(u).get()).toList();
        users.forEach(user1->{followed.add(UserMapper.toUserFollowedDto(user1));});



        return new UserFollowedDto(user.getId(), user.getName(), followed);
    }

    @Override
    public FollowersCountDto getUserFollowersCount(Integer userId) {
        User user = userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        return UserMapper.toFollowersCountDto(user, user.getFollowedBy().size());
    }

    @Override
    public void removeFollowById(Integer userId, Integer userIdToUnfollow) {
        if (userId.equals(userIdToUnfollow)){
            throw new BadRequestException("No es posible generar esta acción");
        }

        User user = userRepositoryImpl.getById(userId).
                orElseThrow(()-> new NotFoundException("Usuario no encontrado"));
        User toUnfollow = userRepositoryImpl.getById(userIdToUnfollow)
                .orElseThrow(()-> new NotFoundException("Usuario a dejar de seguir no encontrado"));

        if (!user.getFollowing().contains(userIdToUnfollow)) {
            throw new BadRequestException("No sigues a este usuario");
        }

        userRepositoryImpl.deleteFollowById(user, toUnfollow);
    }
}
