package com.mercadolibre.be_java_hisp_w31_g04.controller;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
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

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<String> createFollow(@PathVariable Integer userId, @PathVariable Integer userIdToFollow) {
        userServiceImpl.addFollowById(userId, userIdToFollow);
        return new ResponseEntity<>("Follow creado con éxito", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserToCreateDto userToCreateDto) {
        userServiceImpl.createUser(userToCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created: Usuario creado exitosamente.");
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
    public ResponseEntity<UserWithFollowersDto> deleteFollow(@PathVariable Integer userId, @PathVariable Integer userIdToUnfollow) {
        return new ResponseEntity<>(userServiceImpl.removeFollowById(userId, userIdToUnfollow), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable Integer userId) {
        userServiceImpl.removeUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado correctamente.");
    }
}
