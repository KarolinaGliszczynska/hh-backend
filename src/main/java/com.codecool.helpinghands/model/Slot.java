package com.codecool.helpinghands.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events_slot")
public class Slot {
    @Id
    @GeneratedValue
    private int slotId;
    @ManyToOne
    @JoinColumn
    private Event event;
    private LocalDateTime slotStartTime;
    private LocalDateTime slotEndTime;
    private int numOfVolunteers;

    public Slot(Event event, LocalDateTime slotStartTime, LocalDateTime slotEndTime){
        this.event = event;
        this.slotStartTime = slotStartTime;
        this.slotEndTime = slotEndTime;
    }
}
