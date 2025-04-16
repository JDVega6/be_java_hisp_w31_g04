package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.NotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserFollowedDto getUserFollowed(Integer userId, String order) {
        User user= userRepositoryImpl.getById(userId)
                .orElseThrow(() -> new NotFoundException("No se encontró ningún usuario"));

        List<UserFollowedDto> followed=new ArrayList<>();
        List<User> users= user.getFollowing().stream().map(u->userRepositoryImpl.getById(u).get()).toList();
        users.forEach(user1->{followed.add(UserMapper.toUserFollowedDto(user1));});
        if(order.equals("name_asc")) {
            followed.sort(new Comparator<UserFollowedDto>() {
                public int compare(UserFollowedDto obj1, UserFollowedDto obj2) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }
        if(order.equals("name_desc")) {
            followed.sort(new Comparator<UserFollowedDto>() {
                public int compare(UserFollowedDto obj2, UserFollowedDto obj1) {
                    return obj1.getUser_name().compareTo(obj2.getUser_name());
                }
            });
        }




        return new UserFollowedDto(user.getId(), user.getName(), followed);
    }
}
