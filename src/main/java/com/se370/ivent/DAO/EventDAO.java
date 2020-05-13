package com.se370.ivent.DAO;

import com.se370.ivent.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Component
public class EventDAO {
    @Autowired
    private EventRepository repository;

    public Collection<Event> getEvents(String creatorId) {
        return  creatorId == null ? repository.findAll() : repository.findAllByCreatorId(creatorId);
    };

    public Event addEvent(Event event) {
        try {
            if (repository.findByTitle(event.getTitle()) == null)
                return repository.insert(event);
            return null;
        } catch (Exception e) {
            return repository.insert(event);
        }
    }

    public Event updateEvent(String id, Map<String, String> json) {
        Optional<Event> event = repository.findById(Integer.parseInt(id));

        if (!event.isPresent())
            return null;

        event.get().fromJSON(json);
        return event.get();
    }

    public Event joinEvent(String eventId, String userId) {
        Optional<Event> event = repository.findById(Integer.parseInt(eventId));

        if (!event.isPresent())
            return null;

        event.get().setJoinedUsers(userId);
        return event.get();
    }
}
