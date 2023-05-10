package com.codecool.helpinghands.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationDTO {

    private String userNickname;
    private String userEmail;
    private String password;
}
