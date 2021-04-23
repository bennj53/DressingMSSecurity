package com.thewhiterabbits.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private String username;
    private String password;
    private String confirmedPassword;
    private boolean actived;
    private List<UserRoleDTO> roles = new ArrayList<>();

    public UserDTO(String username, String password, String confirmedPassword) {
        this.username = username;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.actived = true;
    }


}
