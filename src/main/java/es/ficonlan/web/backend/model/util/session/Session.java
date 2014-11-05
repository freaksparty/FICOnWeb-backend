package es.ficonlan.web.backend.model.util.session;

import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.model.role.Role;
import es.ficonlan.web.backend.model.user.User;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
public class Session {
	
	private String sessionId;
	private User user;
	private boolean secondpass;
	private String loginName;
	private Set<Role> role;
	private Calendar lastAccess;
	
	private Session(String sessionId, User user){
		this.sessionId = sessionId;
		this.user = user;
		this.secondpass = false;
		this.loginName = user.getLogin();
		this.role=user.getRoles();
		this.lastAccess = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}
	
	public Session(User user) {
		this.sessionId = generateSessionId(user);
		this.user = user;
		this.secondpass = false;
		this.loginName = user.getLogin();
		this.role=user.getRoles();
		this.lastAccess = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
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

	public boolean isSecondpass() {
		return secondpass;
	}

	public void setSecondpass(boolean secondpass) {
		this.secondpass = secondpass;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	@JsonIgnore
	public Calendar getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Calendar lastAccess) {
		this.lastAccess = lastAccess;
	}
}
