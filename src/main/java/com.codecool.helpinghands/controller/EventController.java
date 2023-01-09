package com.codecool.helpinghands.controller;

import com.codecool.helpinghands.dto.EventDTO;
import com.codecool.helpinghands.dto.EventWithSlotsDTO;
import com.codecool.helpinghands.dto.NewEventDTO;
import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.EventCategory;
import com.codecool.helpinghands.service.EventService;
import com.codecool.helpinghands.service.ImageDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class EventController {

    private final EventService eventService;
    private final ImageDataService imageDataService;


    @Autowired
    public EventController(EventService eventService, ImageDataService imageDataService) {
        this.eventService = eventService;
        this.imageDataService = imageDataService;
    }


    @GetMapping("/events")
    public List<EventDTO> getEvents(){
        return eventService.getAllEventsAsEventDTO();
    }

    @GetMapping("/events/{eventId}")
    public EventWithSlotsDTO getEventById(
            @PathVariable("eventId") int eventId
    ){
        return eventService.getEventDtoByEventId(eventId);
    }

    @PostMapping(value = "/events/createNew", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEvent(@RequestBody NewEventDTO e) {
        Event event = eventService.addEvent(e.getCity(), e.getEventCategory(), e.getEventDescription(), e.getEventTitle(), "", "XX", e.getDateOfEvent(), e.getAddress(), e.getPostalCode());
        EventDTO eventDTO = eventService.convertEventToEventDto(event);

        System.out.println("New event created");
        System.out.println(eventDTO);

        String resp_str = "";
        try {
            var resp = new HashMap<String, String>();
            resp.put("ok", "Created ok");
            resp.put("id", String.valueOf(eventDTO.getEventId()));
            resp_str = (new ObjectMapper()).writeValueAsString(resp);
        } catch (JsonProcessingException exc) {
            resp_str = "Error!";
        }

        return new ResponseEntity<>(resp_str , HttpStatus.OK);
    }


    @PostMapping("/events")
    public ResponseEntity<EventDTO> addEvent(
            //RequestBody
            @RequestParam("city") String city,
            @RequestParam("eventCategory") EventCategory eventCategory,
            @RequestParam("eventDescription") String eventDescription,
            @RequestParam("eventTitle") String eventTitle,
            @RequestParam("imagePath") String imagePath,
            @RequestParam("slots") String slots,
            @RequestParam("dateOfEvent") String dateOfEvent,
            @RequestParam("address") String address,
            @RequestParam("postalCode") String postalCode
    ){
        Event event = eventService.addEvent(city, eventCategory, eventDescription, eventTitle, imagePath, slots, dateOfEvent, address, postalCode);
        EventDTO eventDTO = eventService.convertEventToEventDto(event);
        return new ResponseEntity<>(
                eventDTO,
                HttpStatus.OK);
    }

    @PostMapping("/events/{eventId}/slot")
    public ResponseEntity<EventWithSlotsDTO> addSlotToEvent(
        @PathVariable("eventId") int eventId,
        @RequestParam ("slotStartHour") int slotStartHour,
        @RequestParam ("slotStartMinutes") int slotStartMinutes,
        @RequestParam ("slotEndHour") int slotEndHour,
        @RequestParam ("slotEndMinutes") int slotEndMinutes
    ){
        Event event = eventService.addSlotToEvent(eventId, slotStartHour, slotStartMinutes, slotEndHour, slotEndMinutes);
        EventWithSlotsDTO eventWithSlotsDTO = eventService.convertEventToEventWithSlotsDto(event);
        return new ResponseEntity<>(
                eventWithSlotsDTO,
                HttpStatus.OK);
    }

    @PostMapping("/events/{eventId}/uploadPicture")
    public ResponseEntity<String> uploadImage(
            @PathVariable("eventId") int eventId,
            @RequestParam("image") MultipartFile file) throws IOException {
        Event event = eventService.getEventById(eventId);
        imageDataService.addPictureToEvent(file, event);
        return new ResponseEntity<>(
                "New image added",
                HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/")
    public String mainPage(){
        return "This is Helping Hands app";
    }

    @GetMapping("/events/city/{cityName}")
    public List<EventDTO> searchEventsByCity(@PathVariable("cityName") String cityName){
        return eventService.getEventsByCity(cityName);
    }

    @GetMapping("/events/category/{category}")
    public List<EventDTO> searchEventsByCategory(@PathVariable("category") String category){
        System.out.println(category);
        return eventService.getEventsByCategory(category);
    }

}


