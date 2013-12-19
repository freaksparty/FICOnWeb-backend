package es.ficonlan.web.backend.test.userservice;

import java.util.List;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.role.RoleDao;
import es.ficonlan.web.backend.model.supportedlanguage.SupportedLanguageDao;
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
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Daniel Gómez Silva
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
        User user = userService.addUser(anonymousSession.getSessionId(), new User("Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", "L"));//¿El tamaño de camiseta un numero o una letra?
        User user2 = userDao.find(user.getUserId());
        assertEquals(user, user2);
    }

    
    @Test
    @SuppressWarnings("unused")
    public void testDuplicatedUser() {
    	try{
        	Session anonymousSession = userService.newAnonymousSession();
            User user = userService.addUser(anonymousSession.getSessionId(),  new User("Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", "L"));//¿El tamaño de camiseta un numero o una letra?
            User user2 = userService.addUser(anonymousSession.getSessionId(),  new User("Ramón", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", "L"));
            fail("This should have thrown an exception");  
    	}catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("addUser"));
        	assertTrue(e.getErrorCode()==03);
        }
    }
        
    @Test
    public void testLogin() throws ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
    	assertEquals(s.getUser(), user);
    }
    
    @Test
    public void testLoginWithSessionStarted() {
    	try{
        	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
        	userDao.save(user);
        	Session anonymousSession = userService.newAnonymousSession();
        	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
        	userService.login(s.getSessionId(),"login1", "pass");
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==07);
        }
    }

	@Test
    public void testNonExistentLogin() {
    	try{
        	Session anonymousSession = userService.newAnonymousSession();
        	userService.login(anonymousSession.getSessionId(), NON_EXISTENT_LOGIN, "");	
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==04);
        }
    }
	
	@Test
    public void testLoginWithIncorrectPassword() {
    	try{
        	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
        	userDao.save(user);
        	Session anonymousSession = userService.newAnonymousSession();
        	userService.login(anonymousSession.getSessionId(),"login1", NON_EXISTENT_PASSWORD);
            fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==04);
        }
    }
	
	@Test
	public void testCloseSession() throws ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
    	userService.closeSession(s.getSessionId());
    	try{
    		userService.login(s.getSessionId(),"login1", "pass");
    	    fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("login"));
        	assertTrue(e.getErrorCode()==01);
        }
	}
	
	@Test
	public void testCloseNonExistentSession() {
		try{
			userService.closeSession(NON_EXISTENT_SESSION_ID);
    	    fail("This should have thrown an exception");  
        }catch(ServiceException e){
        	assertTrue(e.getUseCase().contentEquals("closeSession"));
        	assertTrue(e.getErrorCode()==01);
        }	
	}
	
	@Test
	public void testChangeUserData() throws ServiceException, InstanceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
    	userService.changeUserData(s.getSessionId(), new User(user.getUserId(),"new name", "87654321Y", "newEmail@gmail.com", "666666666", "XL"));
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getName().contentEquals("new name"));
    	assertTrue(u.getDni().contentEquals("12345678R")); //DNI can't be changed by the user.
    	assertTrue(u.getEmail().contentEquals("newEmail@gmail.com"));
    	assertTrue(u.getPhoneNumber().contentEquals("666666666"));
    	assertTrue(u.getShirtSize().contentEquals("XL"));
	}
	
	@Test
	public void testChangeUserDataByAnAdmin() throws ServiceException, InstanceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserData");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	userService.changeUserData(s.getSessionId(),  new User(user.getUserId(),"new name", "87654321Y", "newEmail@gmail.com", "666666666", "XL"));
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getName().contentEquals("new name"));
    	assertTrue(u.getDni().contentEquals("87654321Y")); //DNI can be changed by an admin.
    	assertTrue(u.getEmail().contentEquals("newEmail@gmail.com"));
    	assertTrue(u.getPhoneNumber().contentEquals("666666666"));
    	assertTrue(u.getShirtSize().contentEquals("XL"));
	}
	
	@Test
	public void testChangeUserDataWithNoPermission() throws InstanceException, ServiceException {
    	User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	User user2 = new User("User2", "login2", "pass", "22321321R", "user2@gmail.com", "690047407", "L");
    	userDao.save(user2);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login2", "pass");
    	try {
			userService.changeUserData(s.getSessionId(), new User(user.getUserId(), "new name", "87654321Y", "newEmail@gmail.com", "666666666", "XL"));
		    fail("This should have thrown an exception");  
		} catch (ServiceException e) {
			assertTrue(e.getUseCase().contentEquals("changeUserData"));
        	assertTrue(e.getErrorCode()==02);
		}
	}
	
	@Test
	public void testChangeNonExistentUserData() throws InstanceException, ServiceException {
		User admin = new User("Admin1", "admin", "pass", "12345678R", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserData");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	try {
			userService.changeUserData(s.getSessionId(), new User(NON_EXISTENT_USER_ID, "new name", "87654321Y", "newEmail@gmail.com", "666666666", "XL"));
		    fail("This should have thrown an exception");  
		} catch (ServiceException e) {
			assertTrue(e.getUseCase().contentEquals("changeUserData"));
        	assertTrue(e.getErrorCode()==06);
		}	
	}
	
	@Test
	public void testChangeUserPassword() throws ServiceException, InstanceException{
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
    	userService.changeUserPassword(s.getSessionId(), user.getUserId(), "pass", "newpass");
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getPassword().contentEquals("newpass"));
	}
	
	@Test
	public void testChangeUserPasswordWithIncorrecOldPassword() throws ServiceException {
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"login1", "pass");
    	try {
    		userService.changeUserPassword(s.getSessionId(), user.getUserId(), NON_EXISTENT_PASSWORD, "newpass");
    	    fail("This should have thrown an exception");  
    	} catch (ServiceException e) {
    		assertTrue(e.getUseCase().contentEquals("changeUserPassword"));
    	    assertTrue(e.getErrorCode()==04);
    	}	
	}
	
	@Test
	public void testChangeUserPasswordByAnAdmin() throws ServiceException, InstanceException{
		User user = new User("User1", "login1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
    	userDao.save(user);
    	User admin = new User("Admin1", "admin", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("changeUserPassword");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin", "pass");
    	userService.changeUserPassword(s.getSessionId(), user.getUserId(), null, "newpass"); //Old password not required in this case
    	User u = userDao.find(user.getUserId());
    	assertTrue(u.getPassword().contentEquals("newpass"));
	}
	
	@Test
	public void testGetAllUsers() throws ServiceException {
		User user1 = new User("User1", "user1", "pass", "12345678R", "user1@gmail.com", "690047407", "L");
		User user2 = new User("User2", "user2", "pass", "23232323S", "user2@gmail.com", "690047407", "L");
		User user3 = new User("User3", "user3", "pass", "44343348T", "user3@gmail.com", "690047407", "L");
    	userDao.save(user1);
    	userDao.save(user2);
    	userDao.save(user3);
    	User admin = new User("Admin1", "admin1", "pass", "21321321P", "admin1@gmail.com", "690047407", "L");
    	Role role = new Role("admin");
    	UseCase useCase = new UseCase("getAllUsers");
    	role.getUseCases().add(useCase);
    	admin.getRoles().add(role);
    	useCaseDao.save(useCase);
    	roleDao.save(role);
    	userDao.save(admin);
    	Session anonymousSession = userService.newAnonymousSession();
    	Session s = userService.login(anonymousSession.getSessionId(),"admin1", "pass");
    	List<User> users = userService.getAllUsers(s.getSessionId());
    	assertTrue(users.size()==4);
    	assertTrue(users.get(0).getLogin().contentEquals("user1"));
    	assertTrue(users.get(1).getLogin().contentEquals("user2"));
    	assertTrue(users.get(2).getLogin().contentEquals("user3"));
    	assertTrue(users.get(3).getLogin().contentEquals("admin1"));
	}  
}
