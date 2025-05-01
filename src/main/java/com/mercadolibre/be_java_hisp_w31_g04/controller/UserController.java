package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    IUserService userServiceImpl;

    public UserController( IUserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserToCreateDto userToCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userServiceImpl.createUser(userToCreateDto));
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable int userId){
        return  new ResponseEntity<>(userServiceImpl.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followed/list")
    public ResponseEntity<UserDto> getUserFollowed(@PathVariable Integer userId, @RequestParam(defaultValue = "") String order) {
        return new ResponseEntity<>(userServiceImpl.getUserFollowed(userId, order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<UserWithFollowersDto> getUserFollowers(@PathVariable int userId, @RequestParam(defaultValue = "") String order) {
        return new ResponseEntity<>(userServiceImpl.getUserWithFollowed(userId, order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDto> getUserFollowersCount(@PathVariable Integer userId) {
        return new ResponseEntity<>(userServiceImpl.getUserFollowersCount(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<UserDto> removeFollow(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(userServiceImpl.removeFollow(userId, userIdToUnfollow), HttpStatus.OK);
    }

    @PutMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<UserDto> updateFollow(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        return new ResponseEntity<>(userServiceImpl.updateFollowByUserId(userId, userIdToFollow), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeUser(@PathVariable Integer userId) {
        userServiceImpl.removeUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
