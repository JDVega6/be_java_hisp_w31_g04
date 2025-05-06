package com.mercadolibre.be_java_hisp_w31_g04.service;

import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.exception.UserNotFoundException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.UserRepositoryImpl;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        User userEmpty = CustomFactory.getUserEmpty();
        UserDto expected = CustomFactory.getUserDtoThree();
        UserToCreateDto payload = CustomFactory.getUserToCreate();

        // Act
        doAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            userArg.setId(3);
            return null;
        }).when(userRepository).saveUser(userEmpty);
        UserDto response = userService.createUser(payload);

        // Assert
        verify(userRepository).saveUser(any(User.class));
        assertEquals(expected.getUserId(), response.getUserId());

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

        // Act
        when(userRepository.getById(anyInt())).thenAnswer(invocation -> {
            Integer argument = invocation.getArgument(0);

            if (argument.equals(2)) {
                return user;
            } else if (argument.equals(3)) {
                return CustomFactory.getUserThree();
            } else if (argument.equals(4)) {
                return CustomFactory.getUserFour();
            } else {
                return new User();
            }
        });
        UserDto response = userService.getUserFollowed(id, order);

        // Assert
        verify(userRepository, atLeast(3)).getById(anyInt());
        verify(userRepository, atLeast(1)).orderUsers(users, order);
        assertEquals(expected, response);
    }

    @Test
    void getUserFollowedError() {
        // Arrange
        Optional<User> userEmpty = Optional.empty();
        Integer id = 2;
        String order = "";
        when(userRepository.getById(id)).thenReturn(userEmpty);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserFollowed(id, order));
    }

    @Test
    void getUserWithFollowed() {


    }

    @Test
    void getUserFollowersCount() {
        // Arrange
        Integer id = 2;
        Optional<User> user = CustomFactory.getOptionalUser();
        FollowersCountDto expected = CustomFactory.getFollowersCountFromOptionalUser();

        // Act
        when(userRepository.getById(id)).thenReturn(user);
        FollowersCountDto response = userService.getUserFollowersCount(id);

        // Assert
        verify(userRepository, atLeast(1)).getById(id);
        assertEquals(expected, response);
    }

    @Test
    void getUserFollowersCountError() {
        // Arrange
        Integer id = 1;
        Optional<User> user = Optional.empty();

        // Act and Assert
        when(userRepository.getById(id)).thenReturn(user);
        assertThrows(UserNotFoundException.class, () -> userService.getUserFollowersCount(id));
    }

    @Test
    void removeFollow() {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 4;
        Optional<User> userThreeOpt = CustomFactory.getUserThree();
        Optional<User> userFourOpt = CustomFactory.getUserFour();
        Optional<User> userFiveOpt = CustomFactory.getUserFive();
        User userThree = userThreeOpt.get();
        User userFour = userFourOpt.get();
        User userThreeAfter = CustomFactory.getUserThreeAfterUnfollow();
        UserDto expected = CustomFactory.getUserThreeDtoAfterUnfollow();

        // Act
        when(userRepository.getById(anyInt())).thenAnswer(invocation -> {
            Integer argument = invocation.getArgument(0);

            if (argument.equals(3)) {
                return userThreeOpt;
            } else if (argument.equals(4)) {
                return userFourOpt;
            } else {
                return userFiveOpt;
            }
        });
        when(userRepository.removeFromFollowing(userThree, userFour)).thenReturn(userThreeAfter);
        UserDto response = userService.removeFollow(id, idToUnfollow);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void removeFollow_Error_SameId() {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = id;
        String expected = "No es posible realizar esta acción";

        // Act and Assert
        Exception ex = assertThrows(BadRequestException.class, () -> userService.removeFollow(id, idToUnfollow));
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void removeFollow_Error_DoesNotFollow() {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 2;
        Optional<User> userThreeOpt = CustomFactory.getUserThree();
        Optional<User> userTwoOpt = CustomFactory.getOptionalUser();
        String expected = "No sigues a este usuario";

        // Act and Assert
        when(userRepository.getById(id)).thenReturn(userThreeOpt);
        when(userRepository.getById(idToUnfollow)).thenReturn(userTwoOpt);
        Exception ex = assertThrows(BadRequestException.class, () -> userService.removeFollow(id, idToUnfollow));
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void removeFollow_Error_UserNotFound() {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 4;
        Optional<User> userThreeOpt = Optional.empty();

        // Act and Assert
        when(userRepository.getById(id)).thenReturn(userThreeOpt);
        assertThrows(UserNotFoundException.class, () -> userService.removeFollow(id, idToUnfollow));
    }

    @Test
    void removeFollow_Error_UserToUnfollowNotFound() {
        // Arrange
        Integer id = 3;
        Integer idToUnfollow = 4;
        Optional<User> userThreeOpt = CustomFactory.getUserThree();
        Optional<User> userFourOpt = Optional.empty();
        String expected = "El usuario a dejar de seguir no existe";

        // Act and Assert
        when(userRepository.getById(id)).thenReturn(userThreeOpt);
        when(userRepository.getById(idToUnfollow)).thenReturn(userFourOpt);
        Exception ex = assertThrows(UserNotFoundException.class, () -> userService.removeFollow(id, idToUnfollow));
        assertEquals(expected, ex.getMessage());
    }

    @Test
    void removeUserById() {
    }

    @Test
    void updateFollowByUserId() {
    }
}