package com.develhope.Java27_progetto3_team2.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDTO userDTO){
       return User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .id(userDTO.getId())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .name(user.getName())
                .id(user.getId())
                .surname(user.getSurname())
                .role(user.getRole())
                .build();
    }
}
