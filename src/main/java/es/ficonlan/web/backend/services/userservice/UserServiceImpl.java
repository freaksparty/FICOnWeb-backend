/*
 * Copyright 2020 Asociación Cultural Freak's Party
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ficonlan.web.backend.services.userservice;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.dao.EmailTemplateDao;
import es.ficonlan.web.backend.dao.RoleDao;
import es.ficonlan.web.backend.dao.SupportedLanguageDao;
import es.ficonlan.web.backend.dao.UseCaseDao;
import es.ficonlan.web.backend.dao.UserDao;
import es.ficonlan.web.backend.entities.Email;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.entities.Role;
import es.ficonlan.web.backend.entities.SupportedLanguage;
import es.ficonlan.web.backend.entities.UseCase;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.model.email.EmailFIFO;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.Session;
import es.ficonlan.web.backend.model.util.session.SessionManager;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 * @author Siro González <xiromoreira>
 */
@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final String ADMIN_LOGIN = "Admin";
	private static final String INITIAL_ADMIN_PASS = "initialAdminPass";
		
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UseCaseDao useCaseDao;
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;
	
	@Autowired
	private SupportedLanguageDao languageDao;
	
	private String hashPassword (String password){
		try {
			MessageDigest m = MessageDigest.getInstance("SHA-512");
		
			m.reset();
			m.update(password.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			
			while(hashtext.length() < 32 ){
				hashtext = "0"+hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
		
	/**
	 * Crea los usuarios, roles y casos de uso por defecto, en caso de que no estén creados aún.
	 */
	@Override
	@Transactional
	public void initialize(){
		
//		for(Method m:UserService.class.getMethods()){
//			UseCase uc = useCaseDao.findByName(m.getName());
//			if (uc==null && !m.getName().contentEquals("initialize") &&
//				!m.getName().contentEquals("login") && !m.getName().contentEquals("getCurrenUser")){
//				uc=new UseCase(m.getName());
//				useCaseDao.save(uc);
//			}
//		}
//
//		for(Method m:EventService.class.getMethods()){
//			UseCase uc = useCaseDao.findByName(m.getName());
//			if (uc==null){
//				uc=new UseCase(m.getName());
//				useCaseDao.save(uc);
//			}
//		}
//
//		for(Method m:EmailService.class.getMethods()){
//			UseCase uc = useCaseDao.findByName(m.getName());
//			if (uc==null){
//				uc=new UseCase(m.getName());
//				useCaseDao.save(uc);
//			}
//		}
		
		Role userRole  = roleDao.findByName("User");
		Role adminRole  = roleDao.findByName("Admin");
		Role anonymousRole = roleDao.findByName("Anonymous");
		
		User anonymous = userDao.findUserByLogin("anonymous");
		if (anonymous == null){
			anonymous = new User("-", "anonymous", "anonymous", "-", "-", "-", "-");
			anonymous.getRoles().add(anonymousRole);
	    	userDao.save(anonymous);
		}
		
//		Role eventController = roleDao.findByName("EventController");
//		Role newsController = roleDao.findByName("NewsController");
//		Role sponsorController = roleDao.findByName("SponsorController");
		
		User admin = userDao.findUserByLogin(ADMIN_LOGIN);
		if (admin == null) admin = new User("Administrador", ADMIN_LOGIN, hashPassword(INITIAL_ADMIN_PASS), "0", "adminMail", "-", "-");
		if(!admin.getRoles().contains(adminRole)) admin.getRoles().add(adminRole);
		if(!admin.getRoles().contains(userRole)) admin.getRoles().add(userRole);
//		if(!admin.getRoles().contains(eventController)) admin.getRoles().add(eventController);
//		if(!admin.getRoles().contains(newsController)) admin.getRoles().add(newsController);
//		if(!admin.getRoles().contains(sponsorController)) admin.getRoles().add(sponsorController);
    	userDao.save(admin);
    	
		SessionManager.setDefaultSession(new Session(userDao.findUserByLogin("anonymous")));
	}
	
	@Override
	@Transactional
	public Session newAnonymousSession() {
		User user = userDao.findUserByLogin("anonymous");
		Session s = new Session(user);
		SessionManager.addSession(s);
		return s;
	}
	
	@Override
	@Transactional
	public User addUser(String sessionId, User user) throws ServiceException{
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addUser")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		User u = userDao.findUserByLogin(user.getLogin());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"login");
		u = userDao.findUserByDni(user.getDni());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"dni");
		if(user.getLogin()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"login");
		if(user.getPassword()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"password");
		if(user.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(user.getDni()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"dni");
		u = userDao.findUserByEmail(user.getEmail());
		if (u != null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"email");
		if(user.getEmail()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"email");
		if(user.getDob()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"fecha nacimiento"); //FIXME
		user.getRoles().add(roleDao.findByName("User"));
		String pass = hashPassword(user.getPassword());
		user.setPassword(pass);
		user.setSecondPassword(pass);
		userDao.save(user);
		return user;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Session login(String login, String password) throws ServiceException {
//		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
//		Session session = SessionManager.getSession(sessionId);
//		if(login.contentEquals("anonymous")) return session;
//		if(!session.getLoginName().contentEquals("anonymous")) throw new ServiceException(ServiceException.SESSION_ALREADY_EXISTS);
		User user = userDao.findUserByLogin(login);
		
		boolean secondPass = false;
		
		if (user == null) throw new ServiceException(ServiceException.INCORRECT_FIELD,"");
		if (!user.getPassword().contentEquals(hashPassword(password))) {
			if ((user.getSecondPasswordExpDate()==null) || (user.getSecondPasswordExpDate().before(Calendar.getInstance(TimeZone.getTimeZone("UTC")))) || (!user.getSecondPassword().contentEquals(hashPassword(password)))) {
				throw new ServiceException(ServiceException.INCORRECT_FIELD,"");
			}
			else secondPass = true;
		}
		
		Session session = new Session(user);
		session.setSecondpass(secondPass);
		SessionManager.addSession(session);
		return session;
	}
	
	@Override
	public void closeAllUserSessions(String sessionId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "closeAllUserSessions")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		SessionManager.closeAllUserSessions(userId);
	}
	
	@Override
	public User getCurrenUser(String sessionId) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		Session session = SessionManager.getSession(sessionId);
		if(session.getLoginName().contentEquals("anonymous")) return null;
		try {
			User user = userDao.find(session.getUserId());
			SessionManager.getSession(sessionId).setUserId(user.getUserId());;
			return user;
		}
		catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
		//return session.getUser();
	}

	@Override
	public void closeSession(String sessionId) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		SessionManager.removeSession(sessionId);
	}

	@Override
	@Transactional
	public void changeUserData(String sessionId, int userId, User userData) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		Session session = SessionManager.getSession(sessionId);
		if(!SessionManager.checkPermissions(session, userId, "changeUserData")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try {
			User user = userDao.find(userId);
			if(userData.getName()!=null) user.setName(userData.getName());
			if(session.getUserId()!=userData.getUserId()) if(userData.getDni()!=null) user.setDni(userData.getDni());
			User u = userDao.findUserByEmail(userData.getEmail());
			if(u!=null)
				if(!(u.getUserId()==user.getUserId()))
					throw new ServiceException(ServiceException.DUPLICATED_FIELD,"email");
			if(userData.getEmail()!=null) user.setEmail(userData.getEmail());
			if(userData.getPhoneNumber()!=null )user.setPhoneNumber(userData.getPhoneNumber());
			if(userData.getShirtSize()!=null) user.setShirtSize(userData.getShirtSize());
			if(userData.getDob()!=null) user.setDob(userData.getDob());
			userDao.save(user);
			session.setUser(user);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	/**
	 * Password changed by the user -> Old password required.<br>
	 * Password changed by an admin -> Old password not required.
	 */
	@Override
	@Transactional
	public void changeUserPassword(String sessionId, int userId, String oldPassword, String newPassword) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		Session session = SessionManager.getSession(sessionId);
		if(!SessionManager.checkPermissions(session, userId, "changeUserPassword")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try {
			User user = userDao.find(userId);
			if(session.getUserId() == userId)
			{
				if(!hashPassword(oldPassword).contentEquals(user.getPassword()))
						if ((user.getSecondPasswordExpDate()==null) || (user.getSecondPasswordExpDate().before(Calendar.getInstance(TimeZone.getTimeZone("UTC")))) || (!user.getSecondPassword().contentEquals(hashPassword(oldPassword))))
							throw new ServiceException(ServiceException.INCORRECT_FIELD,"pass");
				user.setSecondPasswordExpDate(null);
			}
			user.setPassword(hashPassword(newPassword));
			user.setSecondPassword(hashPassword(newPassword));
			userDao.save(user);
			
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}
	
	@Override
	@Transactional
	public boolean passwordRecover(String sessionId, String email) throws ServiceException {
		
		int minutos = 30;
		int passtam = 12;
		
		User user = userDao.findUserByEmail(email);
		EmailTemplate template = emailTemplateDao.findByName("passwordRecover");
		
		if((user!=null) && (template!=null)) {
			char[] elementos={'0','1','2','3','4','5','6','7','8','9' ,'a',
					'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
					'u','v','w','x','y','z'};
			
			char[] conjunto = new char[passtam];
			
			for(int i=0;i<passtam;i++){
				int el = (int)(Math.random()*35);
				conjunto[i] = elementos[el];
			}
			String pass = new String(conjunto);
			
			user.setSecondPassword(hashPassword(pass));
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			now.add(Calendar.MINUTE,minutos); //Tiene 30 minutos
			user.setSecondPasswordExpDate(now);
			
			Hashtable<String,String> tabla = new Hashtable<String,String>();
    		tabla.put("#loginusuario", user.getLogin());
    		tabla.put("#nuevapas", pass);
    		tabla.put("#tiemporestante",Integer.toString(minutos));
			 		
    		Email e = emailTemplateDao.findByName("passwordRecover").generateEmail(user, tabla);

    		//if(e.sendMail()) userDao.save(user);
    		//e.sendMail();
    		EmailFIFO.addEmailToQueue(e);
    		
    		userDao.save(user);
    		
    		return true;
    		//Estos Email no los guardo en la BD porque contienen las contraseñas en plano
		}
		return false;
	}

	@Override
	@Transactional(readOnly=true)
	public List<User> getUsersByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc) throws ServiceException {
		return userDao.getUsersByEvent(eventId,state,startindex,maxResults,orderBy,desc);
	}
	
	@Override
	@Transactional(readOnly=true)
	public long getUsersByEventTAM(int eventId, RegistrationState state) throws ServiceException {
		return userDao.getUsersByEventTAM(eventId, state);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> getAllUsers(int startindex, int maxResults, String orderBy, boolean desc) throws ServiceException {
		return userDao.getAllUsers(startindex, maxResults, orderBy, desc);
	}
	
	@Override
	@Transactional(readOnly=true)
	public long getAllUsersTAM() throws ServiceException {
		return userDao.getAllUsersTAM();
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> findUsersByName(String sessionId, String name, int startindex, int maxResults) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "findUsersByName")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return userDao.findUsersByName(name, startindex, maxResults);
	}

	@Override
	@Transactional
	public void addUserToBlackList(String sessionId, int userId) throws ServiceException{
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addUserToBlackList")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			user.setInBlackList(true);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}

	@Override
	@Transactional
	public void removeUserFromBlackList(String sessionId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeUserFromBlackList")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			user.setInBlackList(false);
			userDao.save(user);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<User> getBlacklistedUsers(String sessionId, int startIndex, int maxResults) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getBlacklistedUsers")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return userDao.getBlacklistedUsers(startIndex, maxResults);
	}
	
	@Override
	@Transactional
	public void setDefaultLanguage(String sessionId, int userId, int languageId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "setDefaultLanguage")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			SupportedLanguage language = languageDao.find(languageId);
			user.setDefaultLanguage(language);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Language");
		}
	}

	@Override
	@Transactional
	public void removeUser(String sessionId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "removeUser")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		else try {
			    SessionManager.closeAllUserSessions(userId);
				userDao.remove(userId);
			} catch (InstanceException e) {
				throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			}
	}
	
	@Override
	@Transactional
	public Role createRole(String sessionId, String rolename) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "createRole")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		Role rol = roleDao.findByName(rolename);
		
		if(rol==null)
		{
			rol = new Role(rolename);
			roleDao.save(rol);
			return rol;
		}
		else throw new ServiceException(ServiceException.DUPLICATED_FIELD);
		
	}
	
	@Override
	@Transactional
	public void removeRole(String sessionId, int roleid) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeRole")) throw new ServiceException(ServiceException.PERMISSION_DENIED);

		try
		{
			roleDao.remove(roleid);
		}
		catch (InstanceException e)
		{
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"role");
		}
		
	}

	@Override
	@Transactional
	public void addRole(String sessionId, int roleId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addRole")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().add(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Role");
		}
	}

	@Override
	@Transactional
	public void removeRole(String sessionId, int roleId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeRole")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			Role role = roleDao.find(roleId);
			user.getRoles().remove(role);
			userDao.save(user);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Role");
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Set<Role> getUserRoles(String sessionId, int userId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getUserRoles")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			User user = userDao.find(userId);
			return user.getRoles();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Role> getAllRoles(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllRoles")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return roleDao.getAllRoles();
	}

	@Override
	@Transactional
	public UseCase createUseCase(String sessionId, String useCaseName) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "createUseCase")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		UseCase useCase = new UseCase(useCaseName);
		useCaseDao.save(useCase);
		return useCase;
	}

	@Override
	@Transactional
	public void addPermission(String sessionId, int roleId, int useCaseId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addPermission")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().add(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"UseCase");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Role");
		}
		
	}

	@Override
	@Transactional
	public void removePermission(String sessionId, int roleId, int useCaseId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removePermission")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Role role = roleDao.find(roleId);
			UseCase useCase = useCaseDao.find(useCaseId);
			role.getUseCases().remove(useCase);
			roleDao.save(role);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("UseCase")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"UseCase");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Role");
		}
		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Set<UseCase> getRolePermissions(String sessionId, int roleId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getRolePermissions")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Role role = roleDao.find(roleId);
			return role.getUseCases();
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Role");
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<UseCase> getAllUseCases(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllUseCases")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return useCaseDao.getAll();
	}
	
}
