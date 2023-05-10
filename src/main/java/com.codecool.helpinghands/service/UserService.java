package com.codecool.helpinghands.service;

import com.codecool.helpinghands.configuration.jwt.JwtUtils;
import com.codecool.helpinghands.dto.RegistrationDTO;
import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.Slot;
import com.codecool.helpinghands.model.User;
import com.codecool.helpinghands.repository.EventRepository;
import com.codecool.helpinghands.repository.UserEventRoleRepository;
import com.codecool.helpinghands.repository.UserRepository;
import com.codecool.helpinghands.validator.WrongInputException;
import com.codecool.helpinghands.validator.registrationValidators.RegistrationValidatorFacade;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SlotService slotService;
    @Autowired
    private UserEventRoleService userEventRoleService;
    @Autowired
    private UserEventRoleRepository userEventRoleRepository;
    @Autowired
    private RegistrationValidatorFacade validatorFacade;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;


    public User getUserById(int userId){

        return userRepository.findById(userId).orElse(null);
    }

    public User assignUserToSlotAndEvent(User user, int slotId){
        Event event = slotService.getEventBySlotId(slotId);
        Slot slot = slotService.getSlotById(slotId);
        if(isUserAssignToEvent(event.getEventId())){
            user.addSlot(slot);
            userRepository.save(user);
        }
        if(isUserAssignToEvent(event.getEventId())) {
            userEventRoleService.assignVolunteerToEvent(user, event);
        }
        return user;
    }


    public boolean isUserAssignToEvent(int eventId){
        Optional<Integer> assignedUserId = userEventRoleRepository.getUserIDFromEvent(eventId);
        return assignedUserId.isEmpty();
    }

    public User findByUserEmail(String userEmail){

        return userRepository.findByUserEmail(userEmail).orElse(null);
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public User deleteUserFromSlotAndEvent(int slotId, User loggedInUser) {
        Slot slotToRemove = slotService.getSlotById(slotId);
        loggedInUser.getUserSlots().remove(slotToRemove);
        userRepository.save(loggedInUser);
        Event event = slotService.getEventBySlotId(slotId);
        userEventRoleRepository.deleteUserEventRoleByEventId(event.getEventId(), loggedInUser.getUserId());
        return  loggedInUser;
    }

    public void verifyUserInput(User user) throws WrongInputException {
        RegistrationDTO userData = modelMapper.map(user, RegistrationDTO.class);
        validatorFacade.validate(userData);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserNickname(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public User getUserFroJWTCookie(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        return userRepository.findByUserNickname(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
