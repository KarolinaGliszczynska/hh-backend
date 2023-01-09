package com.codecool.helpinghands.dto;

import com.codecool.helpinghands.model.EventCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@NoArgsConstructor
@Data
public class NewEventDTO {
    private int eventId;
    private String eventTitle;
    private String dateOfEvent;
    private EventCategory eventCategory;
    private String eventDescription;
    private String address;
    private String city;
   private String postalCode;
    private byte[] image;
    private String[] eventSlots;
    private byte[] eventFiles;
}