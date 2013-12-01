/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.test.eventservice;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.eventservice.EventService;
import static es.ficonlan.web.backend.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.ficonlan.web.backend.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import es.ficonlan.web.backend.util.exceptions.InstanceNotFoundException;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Pereiro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class EventServiceTest {

    private final String NON_EXISTENT_EVENT_NAME = "Boring event";

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventService eventService;

    @Test
    public void testFindEventByName() throws InstanceNotFoundException {
        Event event = new Event(0, "Awesome event!", "Curling world cup.", 10,
                Calendar.getInstance(), Calendar.getInstance(),
                Calendar.getInstance(), Calendar.getInstance());
        eventDao.save(event);
        Assert.assertEquals(event, eventService.findEventByName(1l, "Awesome event!"));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindEventByUnexistingName() throws InstanceNotFoundException {
        eventService.findEventByName(1l, NON_EXISTENT_EVENT_NAME);
    }

}
