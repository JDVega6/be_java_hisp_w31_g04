package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.UserServiceImpl;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    IUserService userServiceImpl;
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<UserFollowedDto> getUserFollowed(@PathVariable Integer userId){
        return new ResponseEntity<>(userServiceImpl.getUserFollowed(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDto> getUserFollowersCount(@PathVariable Integer userId){
        return new ResponseEntity<>(userServiceImpl.getUserFollowersCount(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> deleteFollow(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        userServiceImpl.removeFollowById(userId, userIdToUnfollow);
        return new ResponseEntity<>("Unfollow realizado con éxito", HttpStatus.OK);
    }

}
