/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.test.eventservice;

import es.ficonlan.web.backend.model.activity.Activity;
import es.ficonlan.web.backend.model.activity.Activity.ActivityType;
import es.ficonlan.web.backend.model.activity.ActivityDao;
import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.email.EmailFIFO;
import es.ficonlan.web.backend.model.emailadress.Adress;
import es.ficonlan.web.backend.model.emailadress.AdressDao;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplate;
import es.ficonlan.web.backend.model.emailtemplate.EmailTemplateDao;
import es.ficonlan.web.backend.model.event.Event;
import es.ficonlan.web.backend.model.event.EventDao;
import es.ficonlan.web.backend.model.eventservice.EventService;
import es.ficonlan.web.backend.model.newsitem.NewsDao;
import es.ficonlan.web.backend.model.newsitem.NewsItem;
import es.ficonlan.web.backend.model.registration.Registration;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;
import es.ficonlan.web.backend.model.registration.RegistrationDao;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.usecase.UseCaseDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.util.RegistrationData;
import static es.ficonlan.web.backend.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.ficonlan.web.backend.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
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
    private final String ADMIN_LOGIN = "Admin";
    private final String ADMIN_PASS = "initialAdminPass";

    @Autowired
    private EventDao eventDao;
    
    @Autowired
    private ActivityDao activityDao;
    
    @Autowired
    private NewsDao newsDao;
    
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
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
	@Autowired
	private AdressDao adressDao;
	 
	@Before 
	public void initialize() {
	     userService.initialize();
	}
	
    @Test
    public void testCreateEvent() throws ServiceException, InstanceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(), null, null, null, null, null);
    	eventService.createEvent(s.getSessionId(), event);
    	assertTrue(eventDao.find(event.getEventId()).getEventId()==event.getEventId());
    	
    }
    
    @Test
    public void testChangeEventData() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	EmailTemplate et1 = new EmailTemplate();
    	et1.setAsunto(""); et1.setContenido(""); et1.setFilename(""); et1.setFilepath(""); et1.setName("");
    	emailTemplateDao.save(et1);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(), et1, et1, et1, et1, et1);
    	eventDao.save(event);
    	Calendar newDate = Calendar.getInstance();
    	newDate.set(1, 2, 2015);
    	Event newEventData = new Event(event.getEventId(),"FicOnLan 2015","FicOnLan 2015", 200, newDate, newDate, newDate, newDate, et1, et1, et1, et1, et1);
    	eventService.changeEventData(s.getSessionId(), event.getEventId(), newEventData);
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
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar regCloseDate = Calendar.getInstance();
    	regCloseDate.add(Calendar.DAY_OF_YEAR, 1);
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventDao.save(event);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Registration r = eventService.addParticipantToEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	assertTrue(r!=null);
    	assertTrue(r.getUser().getUserId()==user.getUserId());
    	assertTrue(r.getEvent().getEventId()==event.getEventId());
    	assertTrue(r.getState()==RegistrationState.registered);	
    }
    
    @Test
    public void testRemoveParticipantFromEvent() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(), null, null, null, null, null);
    	eventDao.save(event);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Registration r = new Registration(user,event);
    	registrationDao.save(r);
    	eventService.removeParticipantFromEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	r = registrationDao.findByUserAndEvent(user.getUserId(), event.getEventId());
    	assertTrue(r==null);
    }
    
    @Test
    public void testChangeRegistrationState() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	EmailTemplate et1 = new EmailTemplate();
    	et1.setAsunto(""); et1.setContenido(""); et1.setFilename(""); et1.setFilepath(""); et1.setName("");
    	emailTemplateDao.save(et1);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance(), et1, et1, et1, et1, et1);
    	eventDao.save(event);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Registration r = new Registration(user,event);
    	registrationDao.save(r);
    	assertTrue(r.getState()==RegistrationState.registered);	
    	eventService.changeRegistrationState(s.getSessionId(), user.getUserId(), event.getEventId(), RegistrationState.inQueue);
    	r = registrationDao.findByUserAndEvent(user.getUserId(), event.getEventId());
    	assertTrue(r.getState()==RegistrationState.inQueue);		
    }
    
    @Test
    public void testFindEventByName() throws ServiceException {
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
        Event event = new Event(0, "Awesome event!", "Curling world cup.", 10, Calendar.getInstance(), Calendar.getInstance(),Calendar.getInstance(), Calendar.getInstance(), null, null, null, null, null);
        eventDao.save(event);
        List<Event> result = eventService.findEventByName(s.getSessionId(), "awesome");
        assertTrue(result.size()==1);
        assertTrue(result.get(0).getEventId()==event.getEventId());
    }

    @Test
    public void testFindEventByUnexistingName() throws ServiceException {
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	List<Event> result = eventService.findEventByName(s.getSessionId(), NON_EXISTENT_EVENT_NAME);
    	assertTrue(result.size()==0);
    }
    
    @Test
    public void testAddActivity() throws ServiceException, InstanceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventDao.save(event);
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), 
    													new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	assertTrue(activityDao.find(activity.getActivityId()).getActivityId()==activity.getActivityId());
    }
    
    @Test
    public void testRemoveActivity() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventDao.save(event);
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	eventService.removeActivity(s.getSessionId(), activity.getActivityId());
    	try {
			activityDao.find(activity.getActivityId());
            fail("This should have thrown an exception"); 
		} catch (InstanceException e) {}
    }
    
    @Test
    public void testChangeActivityData() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null,null, null);
    	eventDao.save(event);
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	dateStart.add(Calendar.HOUR, 1);
    	dateEnd.add(Calendar.HOUR, 2);
    	eventService.changeActivityData(s.getSessionId(), activity.getActivityId(), new Activity(activity.getActivityId(), "Concurso de hacking", "Concurso de hacking", 20, ActivityType.Production, false, dateStart,dateEnd,dateStart,dateEnd));
    	assertTrue(activity.getName().contentEquals("Concurso de hacking"));
    	assertTrue(activity.getDescription().contentEquals("Concurso de hacking"));
    	assertTrue(activity.getNumParticipants()==20);
    	assertTrue(activity.getType()==ActivityType.Production);
    	assertTrue(!activity.isOficial());
    	assertTrue(activity.getStartDate().compareTo(dateStart)==0);
      	assertTrue(activity.getEndDate().compareTo(dateEnd)==0);
    	assertTrue(activity.getRegDateOpen().compareTo(dateStart)==0);
      	assertTrue(activity.getRegDateClose().compareTo(dateEnd)==0);
    }
    
    @Test
    public void testAddParticipantToActivity() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Event event = eventService.createEvent(s.getSessionId(), new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null));
    	eventService.addParticipantToEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	eventService.addParticipantToActivity(s.getSessionId(), user.getUserId(), activity.getActivityId());
    	assertTrue(activity.getParticipants().contains(user));
    }
    
    @Test
    public void testRemoveParticipantFromActivity() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);

    	Event event = eventService.createEvent(s.getSessionId(), new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null));
    	eventService.addParticipantToEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	eventService.addParticipantToActivity(s.getSessionId(), user.getUserId(), activity.getActivityId());
    	assertTrue(activity.getParticipants().contains(user));
    	eventService.removeParticipantFromActivity(s.getSessionId(), user.getUserId(), activity.getActivityId());
       	assertTrue(!activity.getParticipants().contains(user));
    }
    
    @Test
    public void testGetActivityParticipants() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	User user1 = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	User user2 = userService.addUser(anonymousSession.getSessionId(), new User("User2", "login2", "pass", "12332218R", "user2@gmail.com", "690047407", "L"));
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	
    	Event event = eventService.createEvent(s.getSessionId(), new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null));
    	eventService.addParticipantToEvent(s.getSessionId(), user1.getUserId(), event.getEventId());
    	eventService.addParticipantToEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	Activity activity = eventService.addActivity(s.getSessionId(), event.getEventId(), new Activity(event, "Torneo de Lol", "Torneo de Lol", 10, ActivityType.Tournament, true, dateStart,dateEnd,dateStart,dateEnd));
    	eventService.addParticipantToActivity(s.getSessionId(), user1.getUserId(), activity.getActivityId());
    	eventService.addParticipantToActivity(s.getSessionId(), user2.getUserId(), activity.getActivityId());
    	List<User> result = eventService.getActivityParticipants(s.getSessionId(), activity.getActivityId());
    	assertTrue(result.size()==2);
    	assertTrue(result.get(0).getUserId()==user1.getUserId());
    	assertTrue(result.get(1).getUserId()==user2.getUserId());
    }
    
    @Test
    public void addNewsTest() throws ServiceException, InstanceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	NewsItem news = new NewsItem("Nueva noticia", Calendar.getInstance(), "http://ficonlan/nuevaNoticia", 2);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, new EmailTemplate(), null, null, null);
    	eventService.createEvent(s.getSessionId(), event);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news);
    	assertEquals(news, newsDao.find(news.getNewsItemId()));
    	assertTrue(newsDao.find(news.getNewsItemId()).getPublisher().getLogin().contentEquals("Admin"));
    }
    
    @Test
    public void changeNewsDataTest() throws ServiceException {
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	NewsItem news = new NewsItem("Nueva noticia", Calendar.getInstance(), "http://ficonlan/nuevaNoticia", 2);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventService.createEvent(s.getSessionId(), event);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news);
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DAY_OF_YEAR, 1);
    	NewsItem newsData = new NewsItem("Nueva noticia2", c , "http://ficonlan/nuevaNoticia2", 3);
    	newsData.setNewsItemId(news.getNewsItemId());
    	eventService.changeNewsData(s.getSessionId(), newsData.getNewsItemId(), newsData);
    	assertTrue(news.getTitle().contentEquals("Nueva noticia2"));
    	assertTrue(news.getContent().contentEquals("http://ficonlan/nuevaNoticia2"));
    	assertTrue(news.getPublishDate().compareTo(c)==0);
    	assertTrue(news.getPriorityHours()==3); 	
    }
   /* 
    @Test
    public void getLastNewsTest() throws ServiceException, InterruptedException {
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.MINUTE, 2);
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventService.createEvent(s.getSessionId(), event);
    	
    	NewsItem news1 = new NewsItem("Nueva noticia1",  Calendar.getInstance(), "http://ficonlan/nuevaNoticia1", 2);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news1);
    	Thread.sleep(200);
    	NewsItem news2 = new NewsItem("Nueva noticia2",  Calendar.getInstance(), "http://ficonlan/nuevaNoticia2", 0);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news2);
    	Thread.sleep(200);
      	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DAY_OF_YEAR, -2);
    	NewsItem news3 = new NewsItem("Nueva noticia3", c, "http://ficonlan/nuevaNoticia3", 0);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news3);
    	Thread.sleep(200);
    	Thread.sleep(10);
    	Calendar limit = Calendar.getInstance();
    	limit.add(Calendar.DAY_OF_YEAR, -1);
    	List<NewsItem> lastNews = eventService.getLastNews(s.getSessionId(), limit, false);
    	assertTrue(lastNews.size()==2);
    	
    	assertTrue(lastNews.get(0).getNewsItemId()==news1.getNewsItemId());
    	assertTrue(lastNews.get(1).getNewsItemId()==news2.getNewsItemId());
    	lastNews = eventService.getLastNews(s.getSessionId(), limit, true);
    	assertTrue(lastNews.size()==1);
    	assertTrue(lastNews.get(0).getNewsItemId()==news1.getNewsItemId());
    	limit.add(Calendar.DAY_OF_YEAR, -5);
    	lastNews = eventService.getLastNews(s.getSessionId(), limit, false);
    	assertTrue(lastNews.size()==3);
    	assertTrue(lastNews.get(0).getNewsItemId()==news1.getNewsItemId());
    	assertTrue(lastNews.get(1).getNewsItemId()==news2.getNewsItemId());
    	assertTrue(lastNews.get(2).getNewsItemId()==news3.getNewsItemId());
    }
    */
    @Test
    public void removeNewsTest() throws ServiceException {
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	NewsItem news = new NewsItem("Nueva noticia", Calendar.getInstance(), "http://ficonlan/nuevaNoticia", 2);
    	Calendar dateStart = Calendar.getInstance();
    	Calendar dateEnd = Calendar.getInstance();
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventService.createEvent(s.getSessionId(), event);
    	eventService.addNews(s.getSessionId(), event.getEventId(), news);
    	try {
			newsDao.find(news.getNewsItemId());
		} catch (InstanceException e1) {
			  fail("This shouldn't have thrown an exception");  
		}
    	eventService.removeNews(s.getSessionId(), news.getNewsItemId());
    	try {
			newsDao.find(news.getNewsItemId());
			fail("This shouldn have thrown an exception");  
		} catch (InstanceException e) {}
    }    
    
    @Test
    public void getRegistrationTest() throws ServiceException{
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	Calendar regCloseDate = Calendar.getInstance();
    	regCloseDate.add(Calendar.DAY_OF_YEAR, 1);
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",150,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventDao.save(event);
    	User user = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L"));
    	Registration r1 = eventService.addParticipantToEvent(s.getSessionId(), user.getUserId(), event.getEventId());
    	Registration r2 = eventService.getRegistration(s.getSessionId(), user.getUserId(), event.getEventId());
    	assertEquals(r1, r2);
    	
    	
    }
    
    
    @Test
    @SuppressWarnings("unused")
    public void RegistrationTest1() throws ServiceException, InterruptedException {
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",2,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	eventDao.save(event);
    	
    	Session anonymousSession = userService.newAnonymousSession(); 
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	
    	User user1 = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "123456781", "user1@gmail.com"	  , "690047407", "L"));
    	User user2 = userService.addUser(anonymousSession.getSessionId(), new User("User2", "login2", "pass", "123456782", "user2@gmail.com"	  , "690047407", "L"));
    	User user3 = userService.addUser(anonymousSession.getSessionId(), new User("User3", "login3", "pass", "123456783", "user3@gmail.com"	  , "690047407", "L"));
    	User user4 = userService.addUser(anonymousSession.getSessionId(), new User("User4", "login4", "pass", "123456784", "surah.harus@gmail.com", "690047407", "L"));
    	User user5 = userService.addUser(anonymousSession.getSessionId(), new User("User5", "login5", "pass", "123456785", "user5@gmail.com"	  , "690047407", "L"));
    	User user6 = userService.addUser(anonymousSession.getSessionId(), new User("User6", "login6", "pass", "123456786", "user6@gmail.com"	  , "690047407", "L"));
    	User user7 = userService.addUser(anonymousSession.getSessionId(), new User("User7", "login7", "pass", "123456787", "user7@gmail.com"	  , "690047407", "L"));

    	
		Registration r1 = eventService.addParticipantToEvent(s.getSessionId(), user1.getUserId(), event.getEventId());
    	Registration r2 = eventService.addParticipantToEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	Registration r3 = eventService.addParticipantToEvent(s.getSessionId(), user3.getUserId(), event.getEventId());
    	Registration r4 = eventService.addParticipantToEvent(s.getSessionId(), user4.getUserId(), event.getEventId());
    	Registration r5 = eventService.addParticipantToEvent(s.getSessionId(), user5.getUserId(), event.getEventId());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.setPaid(s.getSessionId(), user1.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.removeParticipantFromEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println();
    	
    	Registration r6 = eventService.addParticipantToEvent(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println();
    	
    	
    	eventService.setPaid(s.getSessionId(), user3.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.setPaid(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println();
    	
    	event.setNumParticipants(4);
    	eventService.eventNumParticipantsChanged(s.getSessionId(), event.getEventId());

    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println();
    	
    	
    	Registration r7 = eventService.addParticipantToEvent(s.getSessionId(), user7.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(0).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(0).getNumber());
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(1).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(1).getNumber());
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(2).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(2).getNumber());
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(3).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(3).getNumber());
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(4).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(4).getNumber());
    	System.out.println(eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(5).getSize() + "  " +
    			eventService.getShirtSizes(s.getSessionId(),event.getEventId()).get(5).getNumber());
    	
    }
    
    @SuppressWarnings("unused")
	@Test
    public void RegistrationTest2() throws ServiceException {
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",2,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);

    	eventDao.save(event);
    	
    	Session anonymousSession = userService.newAnonymousSession(); 
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	
    	User user1 = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "123456781", "user1@gmail.com"	  , "690047407", "L"));
    	User user2 = userService.addUser(anonymousSession.getSessionId(), new User("User2", "login2", "pass", "123456782", "user2@gmail.com"	  , "690047407", "L"));
    	User user3 = userService.addUser(anonymousSession.getSessionId(), new User("User3", "login3", "pass", "123456783", "user3@gmail.com"	  , "690047407", "L"));
    	User user4 = userService.addUser(anonymousSession.getSessionId(), new User("User4", "login4", "pass", "123456784", "surah.harus@gmail.com", "690047407", "L"));
    	User user5 = userService.addUser(anonymousSession.getSessionId(), new User("User5", "login5", "pass", "123456785", "user5@gmail.com"	  , "690047407", "L"));
    	User user6 = userService.addUser(anonymousSession.getSessionId(), new User("User6", "login6", "pass", "123456786", "user6@gmail.com"	  , "690047407", "L"));
    	User user7 = userService.addUser(anonymousSession.getSessionId(), new User("User7", "login7", "pass", "123456787", "user7@gmail.com"	  , "690047407", "L"));

    	Registration r1 = eventService.addParticipantToEvent(s.getSessionId(), user1.getUserId(), event.getEventId());
    	Registration r2 = eventService.addParticipantToEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	Registration r3 = eventService.addParticipantToEvent(s.getSessionId(), user3.getUserId(), event.getEventId());
    	Registration r4 = eventService.addParticipantToEvent(s.getSessionId(), user4.getUserId(), event.getEventId());
    	Registration r5 = eventService.addParticipantToEvent(s.getSessionId(), user5.getUserId(), event.getEventId());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.setPaid(s.getSessionId(), user1.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.removeParticipantFromEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	Registration r6 = eventService.addParticipantToEvent(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.setPaid(s.getSessionId(), user3.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	
    	eventService.removeParticipantFromEvent(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	r1 = eventService.getRegistration(s.getSessionId(), user1.getUserId(), event.getEventId());
    	//r2 = eventService.getRegistration(s.getSessionId(), user2.getUserId(), event.getEventId());
    	r3 = eventService.getRegistration(s.getSessionId(), user3.getUserId(), event.getEventId());
    	r4 = eventService.getRegistration(s.getSessionId(), user4.getUserId(), event.getEventId());
    	//r5 = eventService.getRegistration(s.getSessionId(), user5.getUserId(), event.getEventId());
    	r6 = eventService.getRegistration(s.getSessionId(), user6.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	List<RegistrationData> lista = eventService.getRegistrationByEvent(s.getSessionId(), event.getEventId(), null, 0, 0, "placeOnQueue", true);
    	lista.stream().forEach(System.out::println);
    	System.out.println(eventService.getRegistrationByEventTAM(s.getSessionId(), event.getEventId(), null));
    }
    
	@Test
    public void RegistrationTest3() throws ServiceException, InterruptedException {
    	
    	EmailFIFO.startEmailQueueThread();
    	
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -1);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 1);
    	
    	Adress adress = new Adress();
    	adress.setUsuarioCorreo("no-responder@freaksparty.org");
    	adress.setPassword("e3MCq5>P2");
    	adressDao.save(adress);
    
    	EmailTemplate outstandingTemplate = new EmailTemplate();
    	outstandingTemplate.setAdress(adress);
    	outstandingTemplate.setAsunto("ASUNTO");
    	outstandingTemplate.setName("Pendiente de pago");
    	outstandingTemplate.setFilename("");
    	outstandingTemplate.setFilepath("");
    	outstandingTemplate.setContenido("Pendiente de pago");
    	
    	emailTemplateDao.save(outstandingTemplate);
    	
    	EmailTemplate onQueueTemplate = new EmailTemplate();
    	onQueueTemplate.setAdress(adress);
    	onQueueTemplate.setAsunto("ASUNTO");
    	onQueueTemplate.setName("En Cola");
    	onQueueTemplate.setFilename("");
    	onQueueTemplate.setFilepath("");
    	onQueueTemplate.setContenido("En Cola");
    	
    	emailTemplateDao.save(onQueueTemplate);
    	
    	EmailTemplate paidTemplate = new EmailTemplate();
    	paidTemplate.setAdress(adress);
    	paidTemplate.setAsunto("ASUNTO");
    	paidTemplate.setName("Pagado");
    	paidTemplate.setFilename("");
    	paidTemplate.setFilepath("");
    	paidTemplate.setContenido("Pagado");
    	
    	emailTemplateDao.save(paidTemplate);
    	
    	EmailTemplate outOfDateTemplate = new EmailTemplate();
    	outOfDateTemplate.setAdress(adress);
    	outOfDateTemplate.setAsunto("ASUNTO");
    	outOfDateTemplate.setName("Fuera de fecha");
    	outOfDateTemplate.setFilename("");
    	outOfDateTemplate.setFilepath("");
    	outOfDateTemplate.setContenido("Fuera de fecha");
    	
    	emailTemplateDao.save(outOfDateTemplate);
    	
    	EmailTemplate onQueueToOutStadingTemplate = new EmailTemplate();
    	onQueueToOutStadingTemplate.setAdress(adress);
    	onQueueToOutStadingTemplate.setAsunto("ASUNTO");
    	onQueueToOutStadingTemplate.setName("De cola a pendiente de pago");
    	onQueueToOutStadingTemplate.setFilename("");
    	onQueueToOutStadingTemplate.setFilepath("");
    	onQueueToOutStadingTemplate.setContenido("De cola a pendiente de pago");
    	
    	emailTemplateDao.save(onQueueToOutStadingTemplate);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",2,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	event.setOutstandingTemplate(outstandingTemplate);
    	event.setOnQueueTemplate(onQueueTemplate);
    	event.setSetPaidTemplate(paidTemplate);
    	event.setOutOfDateTemplate(outOfDateTemplate);
    	event.setFromQueueToOutstanding(onQueueToOutStadingTemplate);
    	
    	eventDao.save(event);
    	
    	Session anonymousSession = userService.newAnonymousSession(); 
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	
    	User user1 = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "123456781", "userfol1@yopmail.com", "690047407", "L"));
    	User user2 = userService.addUser(anonymousSession.getSessionId(), new User("User2", "login2", "pass", "123456782", "userfol2@yopmail.com", "690047407", "L"));
    	User user3 = userService.addUser(anonymousSession.getSessionId(), new User("User3", "login3", "pass", "123456783", "userfol3@yopmail.com", "690047407", "L"));
    	User user4 = userService.addUser(anonymousSession.getSessionId(), new User("User4", "login4", "pass", "123456784", "userfol4@yopmail.com", "690047407", "L"));
    	User user5 = userService.addUser(anonymousSession.getSessionId(), new User("User5", "login5", "pass", "123456785", "userfol5@yopmail.com", "690047407", "L"));
    	User user6 = userService.addUser(anonymousSession.getSessionId(), new User("User6", "login6", "pass", "123456786", "userfol6@yopmail.com", "690047407", "L"));
    	User user7 = userService.addUser(anonymousSession.getSessionId(), new User("User7", "login7", "pass", "123456787", "userfol7@yopmail.com", "690047407", "L"));
    	
    	
    	eventService.addParticipantToEvent(s.getSessionId(), user1.getUserId(), event.getEventId());
    	eventService.addParticipantToEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	eventService.addParticipantToEvent(s.getSessionId(), user3.getUserId(), event.getEventId());
    	eventService.addParticipantToEvent(s.getSessionId(), user4.getUserId(), event.getEventId());
    	eventService.addParticipantToEvent(s.getSessionId(), user5.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.setPaid(s.getSessionId(), user1.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.removeParticipantFromEvent(s.getSessionId(), user2.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    	
    	eventService.removeParticipantFromEvent(s.getSessionId(), user4.getUserId(), event.getEventId());
    	
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();

    	//Thread.sleep(2000000);
    }
    
    @SuppressWarnings("unused")
	@Test
    public void RegistrationTest4() throws ServiceException, InterruptedException {
    	Calendar dateStart = Calendar.getInstance();
    	dateStart.add(Calendar.DAY_OF_YEAR, -2);
    	Calendar dateEnd = Calendar.getInstance();
    	dateEnd.add(Calendar.DAY_OF_YEAR, 2);
    	
    	Event event = new Event(0,"FicOnLan 2014","FicOnLan 2014",3,dateStart,dateEnd,dateStart,dateEnd, null, null, null, null, null);
    	event.setMinimunAge(16);
    	
    	eventDao.save(event);
    	
    	Session anonymousSession = userService.newAnonymousSession(); 
    	Session s = userService.login(anonymousSession.getSessionId(), ADMIN_LOGIN, ADMIN_PASS);
    	
    	User user1 = userService.addUser(anonymousSession.getSessionId(), new User("User1", "login1", "pass", "123456781", "user1@gmail.com"	  , "690047407", "L"));
    	Calendar age = Calendar.getInstance();
    	age.add(Calendar.YEAR, -16);
    	user1.setDob(age);
    	System.out.println(user1.getDob().get(Calendar.YEAR));
    	
    	User user2 = userService.addUser(anonymousSession.getSessionId(), new User("User2", "login2", "pass", "123456782", "user2@gmail.com"	  , "690047407", "L"));
    	User user3 = userService.addUser(anonymousSession.getSessionId(), new User("User3", "login3", "pass", "123456783", "user3@gmail.com"	  , "690047407", "L"));
    	User user4 = userService.addUser(anonymousSession.getSessionId(), new User("User4", "login4", "pass", "123456784", "surah.harus@gmail.com", "690047407", "L"));
    	User user5 = userService.addUser(anonymousSession.getSessionId(), new User("User5", "login5", "pass", "123456785", "user5@gmail.com"	  , "690047407", "L"));
    	User user6 = userService.addUser(anonymousSession.getSessionId(), new User("User6", "login6", "pass", "123456786", "user6@gmail.com"	  , "690047407", "L"));
    	User user7 = userService.addUser(anonymousSession.getSessionId(), new User("User7", "login7", "pass", "123456787", "user7@gmail.com"	  , "690047407", "L"));

    	user2.setDob(age);
    	System.out.println(user2.getDob().get(Calendar.YEAR));
    	user3.setDob(age);
    	System.out.println(user3.getDob().get(Calendar.YEAR));
    	user4.setDob(age);
    	System.out.println(user4.getDob().get(Calendar.YEAR));
    	user5.setDob(age);
    	System.out.println(user5.getDob().get(Calendar.YEAR));
    	user6.setDob(age);
    	System.out.println(user6.getDob().get(Calendar.YEAR));
    	user7.setDob(age);
    	System.out.println(user7.getDob().get(Calendar.YEAR));
    	
    	Registration r1 = eventService.addParticipantToEvent(s.getSessionId(), user1.getUserId(), event.getEventId());

    	Registration r2 = eventService.addParticipantToEvent(s.getSessionId(), user2.getUserId(), event.getEventId());

    	Registration r3 = eventService.addParticipantToEvent(s.getSessionId(), user3.getUserId(), event.getEventId());

    	Registration r4 = eventService.addParticipantToEvent(s.getSessionId(), user4.getUserId(), event.getEventId());

    	Registration r5 = eventService.addParticipantToEvent(s.getSessionId(), user5.getUserId(), event.getEventId());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user1.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user2.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user3.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user4.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user5.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user6.getUserId() ).toString());
    	System.out.println(eventService.getEventRegistrationState(s.getSessionId(), event.getEventId(), user7.getUserId() ).toString());
    	System.out.println();
    }
    
    @Test
    public void RegistrationTest5() throws ServiceException, InterruptedException {
    	
    	EmailFIFO.startEmailQueueThread();
    	
    	Email email; 
    	for(int i = 1;i<10;i++) {
    		email = new Email("no-responder@freaksparty.org","e3MCq5>P2","","","userfol1@yopmail.com","asunto " + Integer.toString(i),"Cuerpo" + Integer.toString(i));
        	EmailFIFO.adEmailToQueue(email);
    	}
    	//Thread.sleep(200000000);
    
    }
}
