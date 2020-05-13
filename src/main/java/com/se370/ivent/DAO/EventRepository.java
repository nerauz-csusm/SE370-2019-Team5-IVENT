package com.se370.ivent.DAO;

import com.se370.ivent.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface EventRepository extends MongoRepository<Event, Integer> {
    Event findByTitle(String title);

    Collection<Event> findAllByCreatorId(String creatorId);
}