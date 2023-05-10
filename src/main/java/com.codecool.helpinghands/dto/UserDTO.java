package com.codecool.helpinghands.dto;


import com.codecool.helpinghands.model.Slot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@Data
public class UserDTO {
    //uzywac typow wysokich (domyslne wartosci nie sa null)
    private int userId;
    private String firstName;
    private String lastName;
    private String userNickname;
    private String userEmail;
    private String password;
    private String userImagePath;
    private boolean isModerator;
}
