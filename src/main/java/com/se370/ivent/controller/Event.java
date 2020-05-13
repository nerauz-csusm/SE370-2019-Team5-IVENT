package com.se370.ivent.controller;

import com.se370.ivent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Collection<com.se370.ivent.models.Event> getEvents(@RequestParam("id") String creatorId) {
        return eventService.getEvents(creatorId == "" ? null : creatorId);
    };

    @PostMapping
    public Map<String, Object> postEvent(@RequestBody com.se370.ivent.models.Event event) {
        Map<String, Object> map = new HashMap<String, Object>();
        com.se370.ivent.models.Event newEvent = eventService.addEvent(event);

        if (newEvent == null) {
            map.put("statusCode", 400);
            map.put("message", "A problem occured");
            return map;
        }

        map.put("statusCode", 200);
        map.put("data", newEvent);

        return map;
    }

    Map<String, Object> eventNotFound(Map<String, Object> map) {
        map.put("statusCode", 400);
        map.put("message", "Event not found");
        return map;
    };

    Map<String, Object> eventFound(Map<String, Object> map, com.se370.ivent.models.Event event) {
        map.put("statusCode", 200);
        map.put("data", event);
        return map;
    };

    @PostMapping(value = "/join")
    public Map<String, Object> joinEvent(@RequestBody Map<String, String> param) {
        Map<String, Object> map = new HashMap<String, Object>();
        com.se370.ivent.models.Event event = eventService.joinEvent(param.get("eventId"), param.get("userId"));

        if (event == null) return eventNotFound(map);

        return eventFound(map, event);
    }

    @PatchMapping
    public Map<String, Object> patchEvent(@RequestBody Map<String, String> param) {
        Map<String, Object> map = new HashMap<String, Object>();
        String eventId = param.get("eventId");

        param.remove("eventId");

        com.se370.ivent.models.Event event = eventService.updateEvent(eventId, param);

        if (event == null) return eventNotFound(map);

        return eventFound(map, event);
    }
}
