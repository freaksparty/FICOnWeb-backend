package es.ficonlan.web.backend.model.eventservice;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.util.exceptions.InstanceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Pereiro
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Transactional(readOnly = true)
    public Event findEventByName(long sessionId, String name) throws InstanceNotFoundException {
        //TODO: Implementación tonta para probar que todo funciona.
        Event event = eventDao.findEventByName(name);
        if (event == null) {
            throw new InstanceNotFoundException(name, Event.class.getName());
        } else {
            return event;
        }
    }

}
