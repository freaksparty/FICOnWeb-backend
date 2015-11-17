package es.ficonlan.web.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonEntityIdSerializer;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Entity
@Table(name="Sponsor")
public class Sponsor {
	
	private int sponsorId;
	private Event event;
	private String name;
	private String url;
	private String imageurl;
	
	public Sponsor() {
		
	}

	@Column(name = "Sponsor_id")
	@SequenceGenerator(name = "SponsorIdGenerator", sequenceName = "SponsorSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SponsorIdGenerator")
	public int getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(int sponsorId) {
		this.sponsorId = sponsorId;
	}

	@JsonSerialize(using = JsonEntityIdSerializer.class)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Sponsor_event_id")
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Column(name = "Sponsor_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Sponsor_url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "Sponsor_imageurl")
	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	
	

}
