package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserWithFollowersDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.UserNotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.service.api.IUserService;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import com.mercadolibre.be_java_hisp_w31_g04.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepositoryImpl userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void createUser() {
        // Arrange
        User userEmpty=CustomFactory.getUserEmpty();
        UserDto expected= CustomFactory.getUserDtoThree();
        UserToCreateDto payload= CustomFactory.getUserToCreate();


        // Act
        doAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            userArg.setId(3);
            return null;
        }).when(userRepository).saveUser(userEmpty);
        UserDto response=userService.createUser(payload);

        // Assert
        verify(userRepository).saveUser(any(User.class));
        assertEquals(expected.getUserId(),response.getUserId());



    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserFollowed() {
        // Arrange
        Optional<User> user = CustomFactory.getOptionalUser();
        Integer id = 2;
        String order = "";
        UserDto expected = CustomFactory.getUserDto();
        List<User> users = CustomFactory.getUserList();
        when(userRepository.getById(anyInt())).thenAnswer(invocation -> {
            Integer argument = invocation.getArgument(0);

            if (argument.equals(2)) {
                return user;
            } else if (argument.equals(3)) {
                return CustomFactory.getUserThree();
            } else if (argument.equals(4)) {
                return CustomFactory.getUserFour();
            }else{
                return new User();
            }
        });

        // Act
        UserDto response=userService.getUserFollowed(id, order);


        // Assert
        verify(userRepository,atLeast(3)).getById(anyInt());
        verify(userRepository,atLeast(1)).orderUsers(users,order);
        assertEquals(expected,response);
    }

    @Test
    void getUserFollowedError() {
        // Arrange
        Optional<User> userEmpty =Optional.empty() ;
        Integer id = 2;
        String order = "";
        when(userRepository.getById(id)).thenReturn(userEmpty);

        // Act and Assert
        assertThrows(UserNotFoundException.class,()->userService.getUserFollowed(id, order));
    }

    @Test
    void getUserWithFollowed() {


    }

    @Test
    void getUserFollowersCount() {
    }

    @Test
    void removeFollow() {
    }

    @Test
    void removeUserById() {
    }

    @Test
    void updateFollowByUserId() {
    }
}