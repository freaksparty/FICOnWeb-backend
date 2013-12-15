package es.ficonlan.web.backend.test.userservice;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.supportedlanguage.SupportedLanguageDao;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.usecase.UseCaseDao;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.userservice.Session;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import static es.ficonlan.web.backend.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.ficonlan.web.backend.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class UserServiceTest {

    private final int NON_EXISTENT_USER_ID = -1;
    private final String NON_EXISTENT_LOGIN = "-2";
    private final String NON_EXISTENT_PASSWORD = "-3";
    private final long NON_EXISTENT_SESSION_ID = -4;


    @Autowired
    private UserService userService;
    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UseCaseDao useCaseDao;
	
	@Autowired
	private SupportedLanguageDao languageDao;
    

    @Test
    public void testCreateRegisterAndFindUser() throws ServiceException, InstanceException {
    	Session anonymousSession = userService.newAnonymousSession();
        User user = userService.addUser(anonymousSession.getSessionId(), "Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);//¿El tamaño de camiseta un numero o una letra?
        User user2 = userDao.find(user.getUserId());
        assertEquals(user, user2);
    }

    
    @Test
    @SuppressWarnings("unused")
    public void testDuplicatedUser() {
    	try{
        	Session anonymousSession = userService.newAnonymousSession();
            User user = userService.addUser(anonymousSession.getSessionId(), "Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);//¿El tamaño de camiseta un numero o una letra?
            User user2 = userService.addUser(anonymousSession.getSessionId(), "Ramón", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);
            fail("This should have thrown an exception");  
    	}catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("addUser"));
        	assertTrue(e.getErrorCode()==3);
        }
    }
        
    @Test
    public void testLogin() throws ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
    	assertEquals(s.getUser(), user);
    }
    
    @Test
    public void testLoginWithSessionStarted() {
    	try{
        	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
        	userDao.save(user);
        	Session anonymousSession = userService.newAnonymousSession();
        	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
        	userService.login(s.getSessionId(),"login1", "pass", false);
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==10);
        }
    }

	@Test
    public void testNonExistentLogin() {
    	try{
        	Session anonymousSession = userService.newAnonymousSession();
        	userService.login(anonymousSession.getSessionId(), NON_EXISTENT_LOGIN, "", false);	
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==4);
        }
    }
	
	@Test
    public void testLoginWithIncorrectPassword() {
    	try{
        	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
        	userDao.save(user);
        	Session anonymousSession = userService.newAnonymousSession();
        	userService.login(anonymousSession.getSessionId(),"login1", NON_EXISTENT_PASSWORD, false);
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==5);
        }
    }
	
	@Test
	public void testCloseSession() throws ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
    	userService.closeSession(s.getSessionId());
    	try{
    		userService.login(s.getSessionId(),"login1", "pass", false);
    	    fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==1);
        }
	}
	
	@Test
	public void testCloseNonExistentSession() {
		try{
			userService.closeSession(NON_EXISTENT_SESSION_ID);
    	    fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("closeSession"));
        	assertTrue(e.getErrorCode()==1);
        }	
	}
	
	@Test
	public void testChangeUserData() throws ServiceException, InstanceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
    	userService.changeUserData(s.getSessionId(), user.getUserId(), "new name", "87654321Y", "newEmail@gmail.com", "666666666", 1);
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getName().contentEquals("new name"));
    	assertTrue(u.getDni().contentEquals("12345678R")); //DNI can't be changed by the user.
    	assertTrue(u.getEmail().contentEquals("newEmail@gmail.com"));
    	assertTrue(u.getPhoneNumber().contentEquals("666666666"));
    	assertTrue(u.getShirtSize()==1);
	}
	
	@Test
	public void testChangeUserDataByAnAdmin() throws ServiceException, InstanceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", 2);
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserData");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass", false);
    	userService.changeUserData(s.getSessionId(), user.getUserId(), "new name", "87654321Y", "newEmail@gmail.com", "666666666", 1);
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getName().contentEquals("new name"));
    	assertTrue(u.getDni().contentEquals("87654321Y")); //DNI can be changed by an admin.
    	assertTrue(u.getEmail().contentEquals("newEmail@gmail.com"));
    	assertTrue(u.getPhoneNumber().contentEquals("666666666"));
    	assertTrue(u.getShirtSize()==1);
	}
	
	@Test
	public void testChangeUserDataWithNoPermission() throws InstanceException, ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	User user2 = new User("User2", "login2", "pass", "22321321R", "user2@gmail.com", "690047407", 2);
    	userDao.save(user2);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login2", "pass", false);
    	try {
			userService.changeUserData(s.getSessionId(), user.getUserId(), "new name", "87654321Y", "newEmail@gmail.com", "666666666", 1);
		    fail("This should have thrown an exception");  
		} catch (ServiceException e) {
			assertTrue(e.getUseCase().contentEquals("changeUserData"));
        	assertTrue(e.getErrorCode()==2);
		}
	}
	
	@Test
	public void testChangeNonExistentUserData() throws InstanceException, ServiceException {
		User admin = new User("Admin1", "admin", "pass", "12345678R", "admin1@gmail.com", "690047407", 2);
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserData");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass", false);
    	try {
			userService.changeUserData(s.getSessionId(), NON_EXISTENT_USER_ID, "new name", "87654321Y", "newEmail@gmail.com", "666666666", 1);
		    fail("This should have thrown an exception");  
		} catch (ServiceException e) {
			assertTrue(e.getUseCase().contentEquals("changeUserData"));
        	assertTrue(e.getErrorCode()==6);
		}	
	}
	
	@Test
	public void testChangeUserPassword() throws ServiceException, InstanceException{
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
    	userService.changeUserPassword(s.getSessionId(), user.getUserId(), "pass", "newpass");
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getPassword().contentEquals("newpass"));
	}
	
	@Test
	public void testChangeUserPasswordWithIncorrecOldPassword() throws ServiceException {
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass", false);
    	try {
    		userService.changeUserPassword(s.getSessionId(), user.getUserId(), NON_EXISTENT_PASSWORD, "newpass");
    	    fail("This should have thrown an exception");  
    	} catch (ServiceException e) {
    		assertTrue(e.getUseCase().contentEquals("changeUserPassword"));
    	    assertTrue(e.getErrorCode()==5);
    	}	
	}
	
	@Test
	public void testChangeUserPasswordByAnAdmin() throws ServiceException, InstanceException{
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", 2);
    	userDao.save(user);
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", 2);
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserPassword");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass", false);
    	userService.changeUserPassword(s.getSessionId(), user.getUserId(), null, "newpass"); //Old password not required in this case
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getPassword().contentEquals("newpass"));
	}
    
}
