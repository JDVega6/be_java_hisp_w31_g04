package com.mercadolibre.be_java_hisp_w31_g04.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercadolibre.be_java_hisp_w31_g04.dto.FollowersCountDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserDto;
import com.mercadolibre.be_java_hisp_w31_g04.dto.UserToCreateDto;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomFactory {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final ObjectWriter writer = mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();

    private static final String USER_CREATE_RESPONSE_PATH = "jsons/user_created_response.json";

    private static final String USER_FOLLOWED_RESPONSE_PATH = "jsons/user_followed_response.json";

    public static Optional<User> getUserOne() {
        List<Integer> following = new ArrayList<>(List.of(3, 4));
        List<Integer> followedBy = new ArrayList<>(List.of(2, 10));
        return Optional.of(new User(1, "Alice", following, followedBy));
    }


    public static Optional<User> getOptionalUser() {
        List<Integer> following = new ArrayList<>(List.of(3, 4));
        List<Integer> followedBy = new ArrayList<>(List.of(1, 10));

        return Optional.of(new User(2, "Eve", following, followedBy));
    }

    public static Optional<User> getUserThree() {
        List<Integer> following = new ArrayList<>(List.of(4, 5));
        List<Integer> followedBy = new ArrayList<>(List.of(1, 2, 10));
        return Optional.of(new User(3, "David", following, followedBy));
    }

    public static Optional<User> getUserFour() {
        List<Integer> following = new ArrayList<>(List.of(5, 6));
        List<Integer> followedBy = new ArrayList<>(List.of(2, 3, 10));
        return Optional.of(new User(4, "Charlie", following, followedBy));
    }
    public static Optional<User> getUserFive() {
        List<Integer> following=new ArrayList<>(List.of(6,7));
        List<Integer> followedBy=new ArrayList<>(List.of(3,4,12));
        return Optional.of(new User(5,"Bob",following,followedBy));
    }
    public static Optional<User> getUserSix() {
        List<Integer> following=new ArrayList<>(List.of(7,8));
        List<Integer> followedBy=new ArrayList<>(List.of(4,5,12));
        return Optional.of(new User(5,"Hank",following,followedBy));
    }
    public static UserDto getUserDtoThree() {
        return new UserDto(3, "David");
    }

    public static UserDto getUserDtoFour() {
        return new UserDto(4, "Charlie");
    }

    public static UserDto getUserDto() {
        List<UserDto> users = new ArrayList<>(List.of(getUserDtoThree(), getUserDtoFour()));
        return new UserDto(2, "Eve", users);
    }

    public static User getUserEmpty() {
        return new User(null, "David", new ArrayList<>(), new ArrayList<>());
    }

    public static List<User> getUserList() {
        List<User> users = new ArrayList<>();
        List<Integer> following = new ArrayList<>(List.of(4, 5));
        List<Integer> followedBy = new ArrayList<>(List.of(1, 2, 10));
        users.add(new User(3, "David", following, followedBy));
        following = new ArrayList<>(List.of(5, 6));
        followedBy = new ArrayList<>(List.of(2, 3, 10));
        users.add(new User(4, "Charlie", following, followedBy));
        return users;
    }

    public static UserToCreateDto getUserToCreate() {
        return new UserToCreateDto("David");
    }

    public static List<User> getUserListAsc() {
        List<User> users = new ArrayList<>();
        List<Integer> following = new ArrayList<>(List.of(5, 6));
        List<Integer> followedBy = new ArrayList<>(List.of(2, 3, 10));
        users.add(new User(4, "Charlie", following, followedBy));
        following = new ArrayList<>(List.of(4, 5));
        followedBy = new ArrayList<>(List.of(1, 2, 10));
        users.add(new User(3, "David", following, followedBy));
        return users;
    }

    public static FollowersCountDto getFollowersCountFromOptionalUser() {
        return new FollowersCountDto(2, "Eve", 2);
    }

    //Integracion

    private static String readJsonFromResource(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    public static UserDto getUserCreatedResponse() throws IOException{
        return generateFromJson(readJsonFromResource(USER_CREATE_RESPONSE_PATH),UserDto.class);
    }

    public static UserDto getUserFollowedResponse() throws IOException{
        return generateFromJson(readJsonFromResource(USER_FOLLOWED_RESPONSE_PATH),UserDto.class);
    }

    public static String getUserToCreatePayload() throws JsonProcessingException {
        return generateFromDto(getUserToCreate());
    }

    public static String getUserFollowersCountResponse() throws JsonProcessingException {
        return generateFromDto(getFollowersCountFromOptionalUser());
    }

    private static <T> T generateFromJson(String data, Class<T> classType) throws JsonProcessingException {
        return mapper.readValue(data, classType);
    }

    private static String generateFromDto(Object dto) throws JsonProcessingException {
        return writer.writeValueAsString(dto);
    }

    public static UserDto getUpdatedUserFollowedResponse() throws IOException {
        UserDto base = getUserFollowedResponse();
        base.getFollowed().add(new UserDto(5, "Bob"));
        return base;
    }

    public static User getUserUpdated(){
        List<Integer> following = new ArrayList<>(List.of(3,6,5));
        List<Integer> followedBy = new ArrayList<>(List.of(2,3,10));
        return new User(4,"Charlie", following,followedBy);
    }
}
