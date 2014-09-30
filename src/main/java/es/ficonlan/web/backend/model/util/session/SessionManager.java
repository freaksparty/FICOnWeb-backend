package es.ficonlan.web.backend.model.util.session;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public class SessionManager {
	
	private static ConcurrentHashMap<String, Session> openSessions = new ConcurrentHashMap<String, Session>();
	
	public static boolean exists(String sessionId){
		return openSessions.containsKey(sessionId);
	}
	
	public static void addSession(Session s){
		openSessions.put(s.getSessionId(), s);
	}
	
	public static Session getSession(String sessionId){
		Session s = openSessions.get(sessionId);
		s.setLastAccess(Calendar.getInstance());
		return s;
	}
	
	public static void removeSession(String sessionId){
		openSessions.remove(sessionId);
	}
	
	public static void cleanOldSessions(int timeout){
		Calendar limitTime = Calendar.getInstance();
		limitTime.add(Calendar.SECOND, -timeout);
		for(Map.Entry<String,Session> e:openSessions.entrySet()){
			if(e.getValue().getLastAccess().before(limitTime))
				openSessions.remove(e.getKey());
		}
	}
	
	/**
	 * If the target user of the operation is the session owner -> Operation allowed<br>
	 * If the target user of the operation isn't the session owner -> Check permissions
	 * 
	 * @param sessionId
	 * @param userId Id of the target user of the operation.
	 * @param useCase
	 * @throws ServiceException
	 */
	public static void checkPermissions(String sessionId, int userId, String useCase) throws ServiceException{
		if (!exists(sessionId)) throw new ServiceException(01,useCase);
		Session session = getSession(sessionId);
		if (session.getUser().getUserId() != userId) checkPermissions(session, useCase);
	}
	
	public static void checkPermissions(String sessionId, String useCase) throws ServiceException {
		if (!exists(sessionId)) throw new ServiceException(01,useCase);
		checkPermissions(openSessions.get(sessionId), useCase);	
	}
	
	public static  void checkPermissions(Session session, String useCase) throws ServiceException{
		for(Role r:session.getUser().getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUseCaseName().contentEquals(useCase)) return;
		    }
		}
		throw new ServiceException(02, useCase);	
	}
}
