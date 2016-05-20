package es.ficonlan.web.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Join entity between user and activities the user is registered.
 * @author Siro Gonz&aacute;lez xiromoreira
 */
@Entity
@Table(name = "User_Activity")
public class UserActivity {
	
	@Id @GeneratedValue
	@Column(name = "User_Activity_id")
	private long userActivityId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "User_Activity_User_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "User_Activity_Activity_id")
	private Activity activity;

	public long getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(long userActivityId) {
		this.userActivityId = userActivityId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
