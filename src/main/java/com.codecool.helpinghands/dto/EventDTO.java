package com.codecool.helpinghands.dto;

import com.codecool.helpinghands.model.EventCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class EventDTO {
    private int eventId;
    private String eventTitle;
    private EventCategory eventCategory;
    private String eventDescription;
    private String city;
    private LocalDate dateOfEvent;
    private byte[] image;

    private String address;
    private String postalCode;
}
