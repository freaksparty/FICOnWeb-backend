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

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.serializer.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.serializer.JsonEntityIdSerializer;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class NewsItem {
	
	private int newsItemId;
	private String title;
	private String imageurl;
	private Calendar creationDate;
	private Calendar publishDate;
	private String content;
	private int priorityHours;
	private User publisher;
	private Event event;
	@Transient
	private String login;
	
	public NewsItem () {};
	
	public NewsItem (String title, Calendar publishDate, String content, int priorityHours) {
		super();
		this.title = title;
		this.creationDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		this.publishDate = publishDate;
		this.content = content;
		this.priorityHours = priorityHours;
	}

	@Column(name = "NewsItem_id")
	@SequenceGenerator(name = "NewsItemIdGenerator", sequenceName = "NewsItemSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NewsItemIdGenerator")
	public int getNewsItemId() {
		return newsItemId;
	}

	public void setNewsItemId(int newsItemId) {
		this.newsItemId = newsItemId;
	}

	@Column(name = "NewsItem_title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "NewsItem_image")
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		if(StringUtils.isEmpty(imageurl))
			this.imageurl = null;
		else
			this.imageurl = imageurl;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "NewsItem_date_created")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name = "NewsItem_date_publish")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Calendar getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Calendar publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "NewsItem_content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "NewsItem_hours_priority")
	public int getPriorityHours() {
		return priorityHours;
	}

	public void setPriorityHours(int priorityHours) {
		this.priorityHours = priorityHours;
	}

	@JsonSerialize(using=JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_User_id")
	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
		this.login = this.publisher.getLogin();
	}
	
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_Event_id")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Transient
	public String getLogin() {
		return this.login;
	}
}
