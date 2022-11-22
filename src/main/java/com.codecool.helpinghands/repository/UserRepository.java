package com.codecool.helpinghands.repository;

import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.EventCategory;
import com.codecool.helpinghands.model.Slot;
import com.codecool.helpinghands.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserEmail(String user_email);


//    List<User> USERS_IN_MEMORY = new ArrayList<>(
//            Arrays.asList(
//                    new User(0, "Barabasz", "Barabejski", "Bari", "bari@buziaczek.pl",
//                       "bari123", null, "", false, -1)
//                )
//
//    );
}
