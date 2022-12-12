package com.codecool.helpinghands.service;

import com.codecool.helpinghands.dto.EventDTO;
import com.codecool.helpinghands.dto.EventWithSlotsDTO;
import com.codecool.helpinghands.dto.SlotDTO;
import com.codecool.helpinghands.model.Event;
import com.codecool.helpinghands.model.EventCategory;
import com.codecool.helpinghands.model.Slot;
import com.codecool.helpinghands.repository.EventRepository;
import com.codecool.helpinghands.repository.SlotRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SlotRepository slotRepository;
    private final ModelMapper modelMapper;
    private final ImageDataService imageDataService;

    @Autowired
    public EventService(EventRepository eventRepository, SlotRepository slotRepository, ModelMapper modelMapper, ImageDataService imageDataService) {

        this.eventRepository = eventRepository;
        this.slotRepository = slotRepository;
        this.modelMapper = modelMapper;
        this.imageDataService = imageDataService;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOfEvent"));
    }

    public List<EventDTO> getAllEventsAsEventDTO (){
        List<Event> events = getAllEvents();
        return events.stream()
                .map(this::convertEventToEventDto)
                .collect(Collectors.toList());
    }


    public List<EventDTO> getEventsByCity(String cityName){
        List<Event> events = eventRepository.findAll().stream().filter(event -> event.getCity().equals(cityName)).toList();
        return events.stream()
                .map(this::convertEventToEventDto)
                .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByCategory(String category){
        List<Event> events = eventRepository.findAll().stream().filter(event -> event.getEventCategory().equals(EventCategory.valueOf(category))).toList();
        return events.stream()
                .map(this::convertEventToEventDto)
                .collect(Collectors.toList());
    }


    public Event getEventById(int eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        return eventOptional.orElse(null);
    }

    public EventWithSlotsDTO getEventDtoByEventId(int eventId){
        Event event = getEventById(eventId);
        return convertEventToEventWithSlotsDto(event);
    }


    public Event addEvent(String city, EventCategory eventCategory, String eventDescription, String eventTitle, String dateOfEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfEventFormatted = LocalDate.parse(dateOfEvent, formatter);
        Event event = new Event(eventTitle, eventDescription, eventCategory, city, dateOfEventFormatted);
        return eventRepository.save(event);
    }

    public Event addSlotToEvent(int eventId, int slotStartHour, int slotStartMinutes, int slotEndHour, int slotEndMinutes){
        Event event = getEventById(eventId);
        LocalDate dateOfEvent = event.getDateOfEvent();
        if (slotStartHour>0 && slotStartHour<25 && slotStartMinutes>=0 && slotStartMinutes<60 && slotEndHour>0 && slotEndHour<25 && slotEndMinutes>=0 && slotEndMinutes<60){
            LocalDateTime slotStartLDT = dateOfEvent.atTime(slotStartHour, slotStartMinutes);
            LocalDateTime slotEndLDT = dateOfEvent.atTime(slotEndHour, slotEndMinutes);
            Slot slot = new Slot (event, slotStartLDT, slotEndLDT);
            slotRepository.save(slot);
            return event;
        }
        return null;
    }


    public void addSlotsToList(EventWithSlotsDTO eventWithSlotsDto) {
        int eventId = eventWithSlotsDto.getEventId();
        Event event = getEventById(eventId);
        Set<Slot> slotsForEvent = slotRepository.findSlotsByEvent(event);
        Set<SlotDTO> slotDTOsForEvent= slotsForEvent.stream()
                                                    .map(this::convertSlotToSlotDto)
                                                    .collect(Collectors.toSet());
        eventWithSlotsDto.setEventSlots(slotDTOsForEvent);
    }

    public SlotDTO convertSlotToSlotDto(Slot slot) {
        SlotDTO slotDto =  modelMapper.map(slot, SlotDTO.class);
        int eventId = slot.getEvent().getEventId();
        slotDto.setEventId(eventId);
        return slotDto;
    }

    public EventDTO convertEventToEventDto(Event event) {
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        addPhotoToEventDto(eventDTO);
        return eventDTO;
    }

    public EventWithSlotsDTO convertEventToEventWithSlotsDto(Event event) {
        EventWithSlotsDTO eventWithSlotsDto = modelMapper.map(event, EventWithSlotsDTO.class);
        addSlotsToList(eventWithSlotsDto);
        addPhotoToEventWithSlotsDto(eventWithSlotsDto);
        return eventWithSlotsDto;
    }

    public void addPhotoToEventWithSlotsDto (EventWithSlotsDTO eventWithSlotsDto) {
        int eventId = eventWithSlotsDto.getEventId();
        Event event = getEventById(eventId);
        byte[] eventPhoto = imageDataService.getImageByEventId(event);
        eventWithSlotsDto.setImage(eventPhoto);
    }

    public void addPhotoToEventDto (EventDTO eventDto) {
        int eventId = eventDto.getEventId();
        Event event = getEventById(eventId);
        byte[] eventPhoto = imageDataService.getImageByEventId(event);
        eventDto.setImage(eventPhoto);
    }


    public void addSlotsToEvent(Event event, List<Integer[]> slots) {
        LocalDate dateOfEvent = event.getDateOfEvent();
        for (Integer[] slot:slots) {
            LocalDateTime slotStartLDT = dateOfEvent.atTime(slot[0], slot[1]);
            LocalDateTime slotEndLDT = dateOfEvent.atTime(slot[2], slot[3]);
            int numOfVolunteers = slot[4];
            Slot newSlot = new Slot (event, slotStartLDT, slotEndLDT, numOfVolunteers);
            slotRepository.save(newSlot);
        }
    }
}
