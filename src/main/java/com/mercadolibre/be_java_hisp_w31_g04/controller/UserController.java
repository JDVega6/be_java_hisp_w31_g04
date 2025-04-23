package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
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
    public ResponseEntity<UserDto> getUserFollowed(@PathVariable Integer userId, @RequestParam(defaultValue = "") String order) {
        return new ResponseEntity<>(userServiceImpl.getUserFollowed(userId,order), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserToCreateDto userToCreateDto) {
        userServiceImpl.createUser(userToCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created: Usuario creado exitosamente.");

    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> createFollow(@PathVariable Integer userId,  @PathVariable Integer userIdToFollow){
        userServiceImpl.addFollowById(userId, userIdToFollow);
        return new ResponseEntity<>("Follow creado con éxito", HttpStatus.OK);
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

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<UserWithFollowersDto> getUserFollowers(@PathVariable int userId, @RequestParam(defaultValue = "") String order){
        return new ResponseEntity<>(userServiceImpl.getUserWithFollowed(userId,order), HttpStatus.OK);
    }
}
