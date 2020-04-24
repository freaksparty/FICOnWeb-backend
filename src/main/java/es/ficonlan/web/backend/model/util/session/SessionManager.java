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

package es.ficonlan.web.backend.model.util.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import es.ficonlan.web.backend.entities.Role;
import es.ficonlan.web.backend.entities.UseCase;

public class SessionManager {
	
	private static ConcurrentHashMap<String, Session> openSessions = new ConcurrentHashMap<String, Session>();
	private static Session defaultSession;
	
	public static boolean exists(String sessionId){
		return openSessions.containsKey(sessionId);
	}
	
	public static void addSession(Session s){
		openSessions.put(s.getSessionId(), s);
	}
	
	public static Session getSession(String sessionId){
		Session s = openSessions.get(sessionId);
		//s.setLastAccess(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
		return s;
	}
	
	public static void removeSession(String sessionId){
		openSessions.remove(sessionId);
	}
	
	/**
	 * @param timeout In seconds
	 */
	public static void cleanOldSessions(int timeout){
		//Calendar limitTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long limitTime = System.currentTimeMillis() - (timeout*1000);
		//limitTime.add(Calendar.SECOND, -timeout);
		for(Map.Entry<String,Session> e:openSessions.entrySet()){
			if(e.getValue().getLastAccess() < limitTime)
			//if(e.getValue().getLastAccess().before(limitTime))
				openSessions.remove(e.getKey());
		}
	}
	
	public static void closeAllUserSessions(int userId) {
		List<String> keys = new ArrayList<String>();
		for (Entry<String, Session> e : openSessions.entrySet()) {
            String key = e.getKey();
            Session value = e.getValue();
            if(value.getUserId()==userId) keys.add(key);
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
		
		session.touch();
		
		if (session.getUserId() == userId) return true;
		else
		for(Role r:session.getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUseCaseName().contentEquals(useCase)) return true;
		    }
		}
		return false;
	}

	public static boolean checkPermissions(Session session, String useCase) {
		
		session.touch();
		
		for(Role r:session.getRoles()){
		    for (UseCase uc: r.getUseCases()){
		    	if (uc.getUseCaseName().contentEquals(useCase)) return true;
		    }
		}
		return false;
	}

	public static Session getDefaultSession() {
		return defaultSession;
	}

	public static void setDefaultSession(Session defaultSession) {
		SessionManager.defaultSession = defaultSession;
	}
}
