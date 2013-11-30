package es.ficonlan.web.backend;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.userservice.UserService;
import es.ficonlan.web.backend.util.exceptions.DuplicatedInstanceException;

public class Main {

	static private UserService userService;
	
	public static void main(String[] args) throws DuplicatedInstanceException {
		
		try {
			@SuppressWarnings("resource")
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
			userService = ctx.getBean(UserService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test registrar usuario:");
	    User u1 = userService.addUser(0, "Pepito", "pep01", "pass", "123456789Y", "pep@yopmail.com", "666666666", 1);
        System.out.println("Usuario "+ u1.getName()+" añadido.");
	    User u2 = userService.addUser(0, "Pepito2", "pep02", "pass", "123456789Y", "pep@yopmail.com", "666666666", 1);
        System.out.println("Usuario "+ u2.getName()+" añadido.");
        
        List<User> users = userService.getAllUsers(0);
        
        System.out.println("Usuarios añadidos:");
        for(User u:users){
            System.out.println("Usuario "+ u.getName()+" añadido.");
        }

	}

}
