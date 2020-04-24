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
