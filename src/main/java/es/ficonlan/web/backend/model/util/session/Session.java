package es.ficonlan.web.backend.model.util.session;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Daniel Gómez Silva
 */
public class Session {

	private static AtomicLong idGenerator = new AtomicLong();
	
	private long sessionId;
	private User user;
	private Calendar lastAccess;
	
	private Session(long sessionId, User user){
		this.sessionId = sessionId;
		this.user = user;
		this.lastAccess = Calendar.getInstance();
	}
	
	public Session(User user) {
		this.sessionId = idGenerator.getAndIncrement();
		this.user = user;
		this.lastAccess = Calendar.getInstance();
	}

	public long getSessionId() {
		return sessionId;
	}

	@JsonSerialize(using=JsonEntityIdSerializer.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Session clone(){
		return new Session(this.getSessionId(), this.getUser());
	}

	@JsonIgnore
	public Calendar getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Calendar lastAccess) {
		this.lastAccess = lastAccess;
	}
}
