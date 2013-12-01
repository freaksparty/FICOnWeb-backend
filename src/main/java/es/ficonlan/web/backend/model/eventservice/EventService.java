package es.ficonlan.web.backend.model.eventservice;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.util.exceptions.InstanceNotFoundException;

/**
 *
 * @author David Pereiro
 */
public interface EventService {

    public Event findEventByName(long sessionId, String name)
            throws InstanceNotFoundException;

}
