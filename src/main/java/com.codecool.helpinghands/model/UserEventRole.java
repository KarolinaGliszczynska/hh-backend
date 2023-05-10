package com.codecool.helpinghands.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class UserEventRole {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn
    private User user;
    @ManyToOne
    @JoinColumn
    private Event event;
    private UserRole roleInEvent;

    public UserEventRole(User user, Event event, UserRole role){
        this.user = user;
        this.event = event;
        this.roleInEvent = role;
    }
}
