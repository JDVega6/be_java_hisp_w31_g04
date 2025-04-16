package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserFollowedDto getUserFollowed(Integer userId) {
        User user= userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        List<UserFollowedDto> followed=new ArrayList<>();
        List<User> users= user.getFollowing().stream().map(u->userRepositoryImpl.getById(u).get()).toList();
        users.forEach(user1->{followed.add(Mapper.toUserFollowedDto(user1));});



        return new UserFollowedDto(user.getId(), user.getName(), followed);
    }
}
