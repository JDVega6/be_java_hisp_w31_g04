package com.mercadolibre.be_java_hisp_w31_g04.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.be_java_hisp_w31_g04.model.User;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Getter
public class UserRepositoryImpl implements IUserRepository{
    private List<User> listOfUsers= new ArrayList<>();

    public UserRepositoryImpl() throws IOException{
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

    @Override
    public Optional<User> getUserFollowed(Integer userId) {
        return listOfUsers.stream().filter(u->u.getId()==userId).findFirst();
    }
}
