package es.ficonlan.web.backend.model.util.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.usecase.UseCase;

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
		s.setLastAccess(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		return s;
	}
	
	public static void removeSession(String sessionId){
		openSessions.remove(sessionId);
	}
	
	public static void cleanOldSessions(int timeout){
		Calendar limitTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		limitTime.add(Calendar.SECOND, -timeout);
		for(Map.Entry<String,Session> e:openSessions.entrySet()){
			if(e.getValue().getLastAccess().before(limitTime))
				openSessions.remove(e.getKey());
		}
	}
	
	public static void closeAllUserSessions(int userId) {
		List<String> keys = new ArrayList<String>();
		for (Entry<String, Session> e : openSessions.entrySet()) {
            String key = e.getKey();
            Session value = e.getValue();
            if(value.getUser().getUserId()==userId) keys.add(key);
        }
		
		for(String s : keys) {
			removeSession(s);
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
	public static boolean  checkPermissions(Session session, int userId, String useCase) {
		if (session.getUser().getUserId() == userId) return true;
		else return checkPermissions(session, useCase);
	}

	public static boolean checkPermissions(Session session, String useCase) {
		for(Role r:session.getUser().getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUseCaseName().contentEquals(useCase)) return true;
		    }
		}
		return false;
	}
}
