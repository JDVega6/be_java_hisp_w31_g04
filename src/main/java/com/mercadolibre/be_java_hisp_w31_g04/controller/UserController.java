package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
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
    public ResponseEntity<UserDto> getUserFollowed(@PathVariable Integer userId, @RequestParam(defaultValue = "name_asc") String order) {
        return new ResponseEntity<>(userServiceImpl.getUserFollowed(userId,order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDto> getUserFollowersCount(@PathVariable int userId){
        return new ResponseEntity<>(userServiceImpl.getUserFollowersCount(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<UserWithFollowersDto> getUserFollowers(@PathVariable int userId){
        return new ResponseEntity<>(userServiceImpl.getUserWithFollowed(userId), HttpStatus.OK);
    }
}
