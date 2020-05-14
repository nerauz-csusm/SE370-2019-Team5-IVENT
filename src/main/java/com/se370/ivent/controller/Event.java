package com.se370.ivent.controller;

import com.se370.ivent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/event")
public class Event {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getEvents(@RequestParam("id") String creatorId) {
        Map<String, Object> response = new HashMap<String, Object>();
        Collection<com.se370.ivent.models.Event> events = eventService.getEvents(creatorId == "" ? null : creatorId);
        return ResponseEntity.status(HttpStatus.OK).body(eventFound(response, events));
    };

    @PostMapping
    public ResponseEntity<Map<String, Object>> postEvent(@RequestBody com.se370.ivent.models.Event event) {
        Map<String, Object> response = new HashMap<String, Object>();
        com.se370.ivent.models.Event newEvent = eventService.addEvent(event);

        if (newEvent == null) {
            response.put("message", "Bad request");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Event created");
        response.put("data", newEvent);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    Map<String, Object> eventNotFound(Map<String, Object> map) {
        map.put("message", "Event not found");
        return map;
    };

    <T> Map<String, Object> eventFound(Map<String, Object> map, T event) {
        map.put("data", event);
        return map;
    };

    @PostMapping(value = "/join")
    ResponseEntity<Map<String, Object>> joinEvent(@RequestBody Map<String, String> param) {
        Map<String, Object> response = new HashMap<String, Object>();
        com.se370.ivent.models.Event event = eventService.joinEvent(param.get("eventId"), param.get("userId"));

        if (event == null) return ResponseEntity.badRequest().body(eventNotFound(response));

        return ResponseEntity.status(HttpStatus.OK).body(eventFound(response, event));
    }

    @PatchMapping
    ResponseEntity<Map<String, Object>> patchEvent(@RequestBody Map<String, String> param) {
        Map<String, Object> response = new HashMap<String, Object>();
        String eventId = param.get("eventId");

        param.remove("eventId");

        com.se370.ivent.models.Event event = eventService.updateEvent(eventId, param);

        if (event == null) return ResponseEntity.badRequest().body(eventNotFound(response));

        return ResponseEntity.status(HttpStatus.OK).body(eventFound(response, event));
    }
}
