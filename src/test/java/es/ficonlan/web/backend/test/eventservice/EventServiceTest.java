/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.test.eventservice;

import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.usecase.UseCaseDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import static es.ficonlan.web.backend.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.ficonlan.web.backend.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertTrue;

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
    private RegistrationDao registrationDao;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private UserService userService;
	
    @Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UseCaseDao useCaseDao;
	    
    @Test
    public void testCreateEvent() throws ServiceException, InstanceException{
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("createEvent");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance());
    	eventService.createEvent(s.getSessionId(), event);
    	assertTrue(eventDao.find(event.getEventId()).getEventId()==event.getEventId());
    	
    }
    
    @Test
    public void testChangeEventData() throws ServiceException{
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeEventData");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance());
    	eventDao.save(event);
    	Calendar newDate = Calendar.getInstance();
    	newDate.set(1, 2, 2015);
    	Event newEventData = new Event(event.getEventId(),"FicOnLan 2015","FicOnLan 2015", 200, newDate, newDate, newDate, newDate);
    	eventService.changeEventData(s.getSessionId(), newEventData);
    	assertTrue(event.getName().contentEquals("FicOnLan 2015"));
    	assertTrue(event.getDescription().contentEquals("FicOnLan 2015"));
    	assertTrue(event.getNumParticipants()==200);
    	assertTrue(event.getStartDate().compareTo(newDate)==0);
    	assertTrue(event.getEndDate().compareTo(newDate)==0);
    	assertTrue(event.getRegistrationOpenDate().compareTo(newDate)==0);
    	assertTrue(event.getRegistrationCloseDate().compareTo(newDate)==0);
    }
    
    @Test
    public void testAddParticipantToEvent() throws ServiceException{
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("addParticipantToEvent");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance());
    	eventDao.save(event);
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	eventService.addParticipantToEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	Registration r = registrationDao.findByUserAndEvent(user.getUserId(), event.getEventId());
    	assertTrue(r!=null);
    	assertTrue(r.getUser().getUserId()==user.getUserId());
    	assertTrue(r.getEvent().getEventId()==event.getEventId());
    	assertTrue(r.getState()==RegistrationState.registered);	
    }
    
    @Test
    public void testRemoveParticipantFromEvent() throws ServiceException{
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("removeParticipantFromEvent");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance());
    	eventDao.save(event);
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Registration r = new Registration(user,event);
    	registrationDao.save(r);
    	eventService.removeParticipantFromEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	r = registrationDao.findByUserAndEvent(user.getUserId(), event.getEventId());
    	assertTrue(r==null);
    }
    
    @Test
    public void testChangeRegistrationState() throws ServiceException{
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeRegistrationState");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance());
    	eventDao.save(event);
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Registration r = new Registration(user,event);
    	registrationDao.save(r);
    	assertTrue(r.getState()==RegistrationState.registered);	
    	eventService.changeRegistrationState(s.getSessionId(), user.getUserId(), event.getEventId(), RegistrationState.inQueue);
    	r = registrationDao.findByUserAndEvent(user.getUserId(), event.getEventId());
    	assertTrue(r.getState()==RegistrationState.inQueue);		
    }
    
    @Test
    public void testFindEventByName() throws ServiceException {
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("findEventByName");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
        Event event = new Event(0, "Awesome event!", "Curling world cup.", 10, Calendar.getInstance(), Calendar.getInstance(),Calendar.getInstance(), Calendar.getInstance());
        eventDao.save(event);
        Assert.assertEquals(event, eventService.findEventByName(s.getSessionId(), "Awesome event!"));
    }

    @Test(expected = ServiceException.class)
    public void testFindEventByUnexistingName() throws ServiceException {
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("findEventByName");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
        eventService.findEventByName(s.getSessionId(), NON_EXISTENT_EVENT_NAME);
    }
}
