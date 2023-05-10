package com.codecool.helpinghands.repository;

import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.User;
import com.codecool.helpinghands.model.UserEventRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.util.Optional;

@Repository
public interface UserEventRoleRepository extends JpaRepository<UserEventRole, Integer>  {
    UserEventRole findByUserAndEvent(User user, Event event);
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEventRole uer where uer.event.eventId = :eventId AND uer.user.userId = :userId")
    void deleteUserEventRoleByEventId(
            @Param("eventId") int eventId,
            @Param("userId") int userId
    );

    @Query("SELECT uer.user.userId FROM UserEventRole uer WHERE uer.event.eventId = :eventId")
    Optional<Integer> getUserIDFromEvent(@Param("eventId") int eventId);
}



