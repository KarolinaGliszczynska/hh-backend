package com.codecool.helpinghands.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    private int eventId;
    private LocalDateTime dateCreated;
    private String eventTitle;
    @Type(type="text")
    private String eventDescription;
    @Enumerated
    private EventCategory eventCategory;
    private String city;
    private LocalDate dateOfEvent;
    private String slots;
    private byte[] image;
    private String address;
    private String postalCode;

    @OneToMany
    @JsonIgnore
    private Set<Slot> eventSlots;

    public Event(String eventTitle, String eventDescription, EventCategory eventCategory, String city, String slots, byte[] image, LocalDate dateOfEvent, String address, String postalCode) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventCategory = eventCategory;
        this.city = city;
        this.slots = slots;
        this.image = image;
        this.dateOfEvent = dateOfEvent;
        this.dateCreated = LocalDateTime.now();
        this.address = address;
        this.postalCode = postalCode;
    }
}
