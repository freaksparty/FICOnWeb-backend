package es.ficonlan.web.backend.model.util.session;

import java.util.concurrent.ConcurrentHashMap;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.usecase.UseCase;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;

public class SessionManager {
	
	private static ConcurrentHashMap<Long, Session> openSessions = new ConcurrentHashMap<Long, Session>();
	
	public static boolean exists(long sessionId){
		return openSessions.containsKey(sessionId);
	}
	
	public static void addSession(Session s){
		openSessions.put(s.getSessionId(), s);
	}
	
	public static Session getSession(long sessionId){
		return openSessions.get(sessionId);
	}
	
	public static void removeSession(long sessionId){
		openSessions.remove(sessionId);
	}
	
	public static void checkPermissions(long sessionId, int userId, String useCase) throws ServiceException{
		if (!exists(sessionId)) throw new ServiceException(01,useCase);
		Session session = getSession(sessionId);
		if (session.getUser().getUserId() != userId) checkPermissions(session, useCase);
	}
	
	public static void checkPermissions(long sessionId, String useCase) throws ServiceException {
		if (!exists(sessionId)) throw new ServiceException(01,useCase);
		checkPermissions(openSessions.get(sessionId), useCase);	
	}
	
	public static  void checkPermissions(Session session, String useCase) throws ServiceException{
		for(Role r:session.getUser().getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUserCaseName().contentEquals(useCase)) return;
		    }
		}
		throw new ServiceException(02, useCase);	
	}
}
