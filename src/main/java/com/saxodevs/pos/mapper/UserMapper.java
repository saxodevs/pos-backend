package com.saxodevs.pos.mapper;

import java.util.List;

import com.saxodevs.pos.dto.UserDTO;
import com.saxodevs.pos.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {

        UserDTO userDto = new UserDTO();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setLastLoginAt(user.getLastLoginAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setCreateAt(user.getCreateAt());
        return userDto;
    }

    public static User toEntity(UserDTO userDto) {

        User user = new User();

        user.setId(userDto.getId());
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setCreateAt(userDto.getCreateAt());
        user.setLastLoginAt(userDto.getLastLoginAt());
        user.setUpdatedAt(userDto.getUpdatedAt());

        return user;

    }

    public static List<UserDTO> userDtoList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).toList();
    }

}
