package com.codecool.helpinghands.dto;

import com.codecool.helpinghands.model.EventCategory;
import com.codecool.helpinghands.model.Slot;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Data
public class EventWithSlotsDTO {
    private int eventId;
    private String eventTitle;
    private EventCategory eventCategory;
    private String eventDescription;
    private String city;
    private LocalDate dateOfEvent;
    private byte[] image;
    private Set<SlotDTO> eventSlots;

    private String address;
    private String postalCode;
}
