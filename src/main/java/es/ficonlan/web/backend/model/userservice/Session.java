package es.ficonlan.web.backend.model.userservice;

import java.util.concurrent.atomic.AtomicLong;

import es.ficonlan.web.backend.model.user.User;

public class Session {

	private static AtomicLong idGenerator = new AtomicLong();
	
	private long sessionId;
	private User user;
	
	public Session(User user) {
		this.sessionId = idGenerator.getAndIncrement();
		this.user = user;
	}

	public long getSessionId() {
		return sessionId;
	}

	public User getUser() {
		return user;
	}
	
}
