package com.se370.ivent.service;

import com.se370.ivent.DAO.EventDAO;
import com.se370.ivent.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class EventService {
    @Autowired
    private EventDAO eventDAO;

    public Collection<Event> getEvents(String creatorId) {
        return eventDAO.getEvents(creatorId);
    }

    public Event addEvent(Event event) {
        return eventDAO.addEvent(event);
    }

    public Event updateEvent(String id, Map<String, String> json) {
        return eventDAO.updateEvent(id, json);
    }

    public Event joinEvent(String eventId, String userId) {
        return eventDAO.joinEvent(eventId, userId);
    }
}
