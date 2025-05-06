package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import com.mercadolibre.be_java_hisp_w31_g04.util.CustomFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    IUserRepository userRepository;

    @BeforeEach
    void setUp() throws IOException {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void saveUser() {
        // Arrange
        User user = CustomFactory.getUserEmpty();
        Integer expected = 1;

        // Act
        userRepository.saveUser(user);

        // Assert
        assertEquals(expected, user.getId());
        userRepository.deleteUserById(1);
    }

    @Test
    void getById() {
        // Arrange
        Optional<User> userExpected = CustomFactory.getOptionalUser();
        Integer id = 2;

        // Act
        Optional<User> userActual = userRepository.getById(id);

        // Assert
        assertEquals(userExpected, userActual);

    }

    @Test
    void getUserId() {
        // Arrange
        int expected = 1;

        // Act
        int id = userRepository.getUserId();

        // Assert
        assertEquals(expected, id);
    }

    @Test
    void orderUsersAsc() {
        // Arrange
        List<User> users = CustomFactory.getUserList();
        List<User> usersExpected = CustomFactory.getUserListAsc();
        String order = "name_asc";

        // Act
        userRepository.orderUsers(users, order);

        // Assert
        assertEquals(usersExpected, users);

    }

    @Test
    void orderUsersDesc() {
        // Arrange
        List<User> users = CustomFactory.getUserList();
        List<User> usersExpected = CustomFactory.getUserList();
        String order = "name_desc";

        // Act
        userRepository.orderUsers(users, order);

        // Assert
        assertEquals(usersExpected, users);

    }

    @Test
    void orderUsersError() {
        // Arrange
        List<User> users = CustomFactory.getUserList();
        String order = "no valido";

        // Act and Assert
        assertThrows(BadRequestException.class, () -> {
            userRepository.orderUsers(users, order);
        });

    }

    @Test
    void removeFromFollowing() {
        // Arrange
        User user = CustomFactory.getUserThree().get();
        User userToUnfollow = CustomFactory.getUserFour().get();
        User userExpected = CustomFactory.getUserThreeAfterUnfollow();

        // Act
        User userRet = userRepository.removeFromFollowing(user, userToUnfollow);

        // Assert
        assertEquals(userExpected, userRet);
    }

    @Test
    void removeFromFollowedBy() {
        // Arrange
        User user = CustomFactory.getUserFour().get();
        User userWhoUnfollowed = CustomFactory.getUserThree().get();

        // Act
        userRepository.removeFromFollowedBy(user, userWhoUnfollowed);

        // Assert
        assertFalse(user.getFollowedBy().contains(userWhoUnfollowed.getId()));
    }

    @Test
    void updateFollowByUserId() {
    }

    @Test
    void deleteUserById() {
    }
}