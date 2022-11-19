package com.example.springboot.dto.user;

import com.example.springboot.model.Enum.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateStatusDto {
    private UserStatus userStatus;
}
