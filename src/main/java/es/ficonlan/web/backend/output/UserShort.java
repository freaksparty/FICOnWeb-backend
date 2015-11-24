package es.ficonlan.web.backend.output;

import es.ficonlan.web.backend.entities.User;

public class UserShort {

	public long userId;
	public String login;
	
	UserShort(User u) {
		userId = u.getUserId();
		login = u.getLogin();
	}
}
