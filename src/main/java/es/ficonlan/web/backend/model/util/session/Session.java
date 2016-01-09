package es.ficonlan.web.backend.model.util.session;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.ficonlan.web.backend.entities.Role;
import es.ficonlan.web.backend.entities.User;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 * @author Siro González Rodríguez
 */
public class Session {
	
	private String sessionId;
	private int userId;
	private String loginName;
	//private User user;
	private boolean secondpass;
	private String userName;
	private Set<Role> roles;
	private long lastAccess;
	
	private Session(String sessionId, Session base){
		this.sessionId = sessionId;
		this.userId = base.getUserId();
		this.secondpass = false;
		this.userName = base.getUserName();
		this.loginName = base.getLoginName();
		this.roles = base.getRoles();
		this.lastAccess = System.currentTimeMillis();
	}
	
	public Session(User user) {
		this.sessionId = generateSessionId(user);
		this.secondpass = false;
		this.loginName = user.getLogin();
		this.userName = user.getName();
		this.roles = user.getRoles();
		this.userId = user.getUserId();
		this.lastAccess = System.currentTimeMillis();
	}
	
	private String hexEncode(byte[] barray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < barray.length; i++) {
			String hex = Integer.toHexString(0xff & barray[i]);
			if (hex.length() == 1)
				sb.append('0');
			sb.append(hex);
		}
		return sb.toString();
	}

	private String generateSessionId(User user) {
		String s = user.getLogin() + user.getName() + user.getPassword()
				+ Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis();
		try {
			MessageDigest mdigest = MessageDigest.getInstance("SHA-256");
			return hexEncode(mdigest.digest(s.getBytes("UTF-8")));
		} catch (Exception e) {
			return null;
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/*@JsonSerialize(using=JsonEntityIdSerializer.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/
	
	public Session clone(){
		return new Session(this.getSessionId(), this);
	}
	
	/**
	 * Set session params to fit user
	 */
	public void setUser(User u) {
		if(u == null) {
			this.userId = -1;
			this.roles = null;
		} else {
			this.userName = u.getName();
			this.loginName = u.getLogin();
			this.userId = u.getUserId();
			this.roles = u.getRoles();
		}
	}

	public boolean isSecondpass() {
		return secondpass;
	}

	public void setSecondpass(boolean secondpass) {
		this.secondpass = secondpass;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String loginName) {
		this.userName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRole(Set<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public long getLastAccess() {
		return lastAccess;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/*public void setLastAccess(Calendar lastAccess) {
		this.lastAccess = lastAccess;
	}*/
	/**
	 * Sets last access to current time
	 */
	public void touch() {
		this.lastAccess = System.currentTimeMillis();
	}
}
