package es.ficonlan.web.backend.model.newsitem;

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

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Calendar;

import es.ficonlan.web.backend.jersey.util.JsonDateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;
import es.ficonlan.web.backend.jersey.util.JsonEventDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonUserDeserializer;
import es.ficonlan.web.backend.model.user.User;
import es.ficonlan.web.backend.model.event.Event;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
public class NewsItem {
	
	private int newsItemId;
	private String title;
	private Calendar creationDate;
	private Calendar publishDate;
	private String url;
	private int priorityHours;
	private User publisher;
	private Event event;
	
	public NewsItem () {};
	
	public NewsItem (String title, Calendar publishDate, String url, int priorityHours) {
		super();
		this.title = title;
		this.creationDate = Calendar.getInstance();
		this.publishDate = publishDate;
		this.url = url;
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

	@Column(name = "NewsItem_url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "NewsItem_hours_priority")
	public int getPriorityHours() {
		return priorityHours;
	}

	public void setPriorityHours(int priorityHours) {
		this.priorityHours = priorityHours;
	}

	@JsonDeserialize(using = JsonUserDeserializer.class)
	@JsonSerialize(using=JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_User_id")
	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	
	@JsonDeserialize(using = JsonEventDeserializer.class)
	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NewsItem_Event_id")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
