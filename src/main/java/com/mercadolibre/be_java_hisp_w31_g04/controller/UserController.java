package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserFollowedDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.UserServiceImpl;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> createFollow(@PathVariable Integer userId,  @PathVariable Integer userIdToFollow){
        userServiceImpl.addFollowById(userId, userIdToFollow);
        return new ResponseEntity<>("Follow creado con éxito", HttpStatus.OK);
    }


    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDto> getUserFollowersCount(@PathVariable int userId){
        return new ResponseEntity<>(userServiceImpl.getUserFollowersCount(userId), HttpStatus.OK);
    }

}
