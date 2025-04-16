package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserFollowedDto getUserFollowed(Integer userId) {
        Optional<User> userRepo= userRepositoryImpl.getUserFollowed(userId);
        if(userRepo.isEmpty()){
            throw new NotFoundException("No se encontro ningun usuario");
        }
        User user=userRepo.get();

        UserFollowedDto userFollowed=new UserFollowedDto(user.getId(),user.getName());

        user.getFollowing().forEach(u->userFollowed.addFollowed(new UserFollowedDto(u.getId(),u.getName())));


        return userFollowed;
    }
}
