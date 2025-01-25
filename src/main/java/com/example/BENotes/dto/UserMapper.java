package com.example.BENotes.dto;

import com.example.BENotes.entity.User;

public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
