package com.codecool.helpinghands.repository;

import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
    Set<Slot> findSlotsByEvent (Event event);


}
