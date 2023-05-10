package com.codecool.helpinghands.controller;


import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.dto.UserDTO;
import com.codecool.helpinghands.dto.EventDTO;
import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.Slot;
import com.codecool.helpinghands.model.User;
import com.codecool.helpinghands.service.EventService;
import com.codecool.helpinghands.service.SlotService;
import com.codecool.helpinghands.service.UserEventRoleService;
import com.codecool.helpinghands.service.UserService;
import com.codecool.helpinghands.validator.WrongInputException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/assign/{slotId}")
    public ResponseEntity<String> assignUserToEventAndSlot(HttpServletRequest request, @PathVariable("slotId") int slotId){
        try {
            //User loggedInUser = userService.getUserFroJWTCookie(request);
            User loggedInUser = userService.getUserById(1);
            userService.assignUserToSlotAndEvent(loggedInUser, slotId);
            System.out.println("User succesfully signed up for event");
        } catch (UsernameNotFoundException e) {
            System.out.println("User could not sign up for event");
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User was added to the chosen slot.", HttpStatus.OK);
    }

    @DeleteMapping("/users/assign/{slotId}")
    public ResponseEntity<String> deleteAssignedUserFromSlotAndEvent(HttpServletRequest request, @PathVariable("slotId") int slotId){
        try {
            User loggedInUser = userService.getUserFroJWTCookie(request);
            userService.deleteUserFromSlotAndEvent(slotId, loggedInUser);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User was removed from the choszn slot.", HttpStatus.OK);
    }
}
