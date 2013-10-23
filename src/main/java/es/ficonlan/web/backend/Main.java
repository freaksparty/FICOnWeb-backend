package es.ficonlan.web.backend;

import org.hibernate.Session;

import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.util.HibernateSessionFactory;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Hibernate test:");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
 
        session.beginTransaction();
        User user = new User(1,"testUser1", "loginUser1", "pass", "123456789P", 1); 
        session.save(user);
        session.getTransaction().commit();
        
        System.out.println("An user has been created.");

	}

}
