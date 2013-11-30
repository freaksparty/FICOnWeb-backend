package es.ficonlan.web.backend.test.userservice;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.user.UserDao;
import es.ficonlan.web.backend.model.userservice.UserService;
import static es.ficonlan.web.backend.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.ficonlan.web.backend.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import es.ficonlan.web.backend.util.exceptions.DuplicatedInstanceException;
import es.ficonlan.web.backend.util.exceptions.InstanceException;
import es.ficonlan.web.backend.util.exceptions.InstanceNotFoundException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class UserServiceTest {

    private final int NON_EXISTENT_USER_ID = -1;
    private final String NON_EXISTENT_PASSWORD = "-2";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateRegisterAndFindUser()
            throws InstanceException {

        User user = userService.addUser(1l, "Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);//¿El tamaño de camiseta un numero o una letra?
        User user2 = userDao.find(user.getUserId());
        assertEquals(user, user2);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentUser() throws InstanceNotFoundException, InstanceException {
        userDao.find(NON_EXISTENT_USER_ID);

    }

    @Test(expected = DuplicatedInstanceException.class)
    @SuppressWarnings("unused")
    public void testDuplicatedUser() throws InstanceNotFoundException,
            DuplicatedInstanceException {
        User user = userService.addUser(1l, "Mon", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);//¿El tamaño de camiseta un numero o una letra?
        User user2 = userService.addUser(2l, "Ramón", "Maetro", "1q2w3e4r", "12345678R", "RMaetro@gmail.com", "690047407", 2);

    }

    /*
     @Test
     public void testCorrectLogin() throws InstanceNotFoundException,
     IncorrectPasswordException, DuplicateInstanceException {

     UserData userData = new UserData("Mon", "Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);
     User user2 = userService.login(user.getLogin(),user.getPassword(), true);

     assertEquals(user, user2);
     // For non-encrypted passwords
     User user3 = userService.login(user.getLogin(), "1234", false);
     assertEquals(user, user3);
     }

     @Test(expected = IncorrectPasswordException.class)
     public void testIncorrectLogin() throws InstanceNotFoundException,
     IncorrectPasswordException, DuplicateInstanceException {
     UserData userData = new UserData("Mon", "Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);

     userService.login(user.getLogin(), NON_EXISTENT_PASSWORD, true);
     }

     // 1

     @Test
     public void testFindUser() throws DuplicateInstanceException, InstanceNotFoundException{
     UserData userData = new UserData("Mon","Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);
     User user2 = userService.findUser(user.getUserId());
     assertEquals(user,user2);
     }

     @Test(expected = InstanceNotFoundException.class)
     public void testFindInvalidUser() throws InstanceNotFoundException{
     @SuppressWarnings("unused")
     User user = userService.findUser(-1);
     }

     @Test
     public void testChangePassword() throws InstanceNotFoundException,
     IncorrectPasswordException, DuplicateInstanceException {


     UserData userData = new UserData("Mon","Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);

     userService.changeUserPassword(user.getUserId(), "1234", "qwerty");
     User user2 = userService.login(user.getLogin(), "qwerty", false);
     assertEquals(user, user2);
     }

     @Test
     public void testChangeUserData() throws DuplicateInstanceException, InstanceNotFoundException{


     UserData userData = new UserData("Mon","Paco@gmail.com");
     UserData userData2 = new UserData( "Loco","Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);

     userService.changeUserData(user.getUserId(), userData2);

     assertEquals(user.getName(),"Loco");
     }

     @Test(expected = InstanceNotFoundException.class)
     public void testNotFoundChangeUserData() throws DuplicateInstanceException, InstanceNotFoundException{

     UserData userData = new UserData("Mon","Paco@gmail.com");

     userService.changeUserData(NON_EXISTENT_USER_ID, userData);

     }


     @Test(expected = IncorrectPasswordException.class)
     public void testChangePasswordLoginOld() throws InstanceNotFoundException,
     IncorrectPasswordException, DuplicateInstanceException {
     UserData userData = new UserData("Mon","Paco@gmail.com");
     User user = userService.registerUser("Maetro", "1234", userData,0);

     userService.changeUserPassword(user.getUserId(), "1234", "qwerty");
     @SuppressWarnings("unused")
     User user2 = userService.login(user.getLogin(), "1234", false);
     }

     */
}
