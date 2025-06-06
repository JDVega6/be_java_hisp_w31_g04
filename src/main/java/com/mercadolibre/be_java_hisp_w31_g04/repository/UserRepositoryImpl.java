package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w31_g04.model.Product;
import com.mercadolibre.be_java_hisp_w31_g04.exception.BadRequestException;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import com.mercadolibre.be_java_hisp_w31_g04.repository.api.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private List<User> listOfUsers= new ArrayList<>();
    private int userId;

    public UserRepositoryImpl() throws IOException{
        userId = 0;
        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> users ;

        file= ResourceUtils.getFile("classpath:users.json");
        users= objectMapper.readValue(file,new TypeReference<List<User>>(){});

        listOfUsers = users;
    }

    public void addFollowById(Integer userId, Integer userIdToFollow) {
        listOfUsers.stream()
                .filter(u -> u.getId() == userId).findFirst()
                .ifPresent(user -> {
                    if(!user.getFollowing().contains(userIdToFollow)){
                        user.getFollowing().add(userIdToFollow);
                    }
                });

        listOfUsers.stream()
                .filter(u -> u.getId() == userIdToFollow)
                .findFirst()
                .ifPresent(followee -> {
                    if (!followee.getFollowedBy().contains(userId)) {
                        followee.getFollowedBy().add(userId);
                    }
                });
    }

    @Override
    public void saveUser(User user) {
        listOfUsers.add(user);

    }

    @Override
    public Optional<User> getById(Integer userId) {
        return listOfUsers.stream().filter(u->u.getId()==userId).findFirst();
    }

    @Override
    public int getUserId() {
        userId ++;
        return userId;
    }

    @Override
    public void orderUsers(List<User> user, String order) {
        if (!order.isEmpty()) {


            if (order.equals("name_asc")) {
                user.sort(new Comparator<User>() {
                    public int compare(User obj1, User obj2) {
                        return obj1.getName().compareTo(obj2.getName());
                    }
                });
            }
            else if (order.equals("name_desc")) {
                user.sort(new Comparator<User>() {
                    public int compare(User obj2, User obj1) {
                        return obj1.getName().compareTo(obj2.getName());
                    }
                });
            }else{
                throw new BadRequestException("Parámetro 'order' inválido. Usa 'name_asc' o 'name_desc'.");
            }
        }
    }

    @Override
    public void deleteFollowById(User user, User toUnfollow) {
        //Parse to Integer is used to enable the remove by Object implementation and to not remove by index
        user.getFollowing().remove(Integer.valueOf(toUnfollow.getId()));
        toUnfollow.getFollowedBy().remove(Integer.valueOf(user.getId()));
    }

    @Override
    public void deleteUserById(Integer userId) {
        listOfUsers.removeIf(u -> u.getId()==userId);
    }
}
