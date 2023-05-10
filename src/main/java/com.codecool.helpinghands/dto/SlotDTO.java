package com.codecool.helpinghands.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class SlotDTO {
    private int slotId;
    private LocalDateTime slotStartTime;
    private LocalDateTime slotEndTime;
    private int eventId;
}
