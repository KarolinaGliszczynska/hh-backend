package com.codecool.helpinghands.service;

import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.Slot;
import com.codecool.helpinghands.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository){
        this.slotRepository = slotRepository;
    }

    public Event getEventBySlotId(int slotId) {

        Slot slot = getSlotById(slotId);
        return slot.getEvent();

    }

    public Slot getSlotById(int slotId){
        Optional<Slot> slotOptional = slotRepository.findById(slotId);
        return slotOptional.orElse(null);
    }


}
