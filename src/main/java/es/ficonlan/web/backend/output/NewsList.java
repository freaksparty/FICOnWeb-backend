package es.ficonlan.web.backend.output;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.EntityTag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.jersey.util.JsonDateSerializer;
import es.ficonlan.web.backend.util.cache.Cacheable;

/*
 * @author Siro González <xiromoreira>
 */
public class NewsList implements Cacheable {

	public static class NewsData {
		public long newsItemId;
		public String title;
		public String imageurl;
		@JsonSerialize(using = JsonDateSerializer.class)
		public Calendar creationDate;
		@JsonSerialize(using = JsonDateSerializer.class)
		public Calendar publishDate;
		public String content;
		public int priorityHours;
		public UserShort publisher;
		
		public NewsData(NewsItem item) {
			newsItemId = item.getNewsItemId();
			title = item.getTitle();
			imageurl = item.getImageurl();
			creationDate = item.getCreationDate();
			publishDate = item.getPublishDate();
			content = item.getContent();
			priorityHours = item.getPriorityHours();
			publisher = new UserShort(item.getPublisher());
		}
	}
	
	public long totalCount;
	public ArrayList<NewsData> list;
	@JsonSerialize(using = JsonDateSerializer.class)
	private Calendar dataExpiration;
	
	public NewsList(List<NewsItem> _list, long count) {
		list = new ArrayList<NewsData>(_list.size());
		totalCount = count;
		for(NewsItem i : _list) {
			list.add(new NewsData(i));
		}
		tag = new EntityTag(Long.toString(System.currentTimeMillis()));
		dataExpiration = Calendar.getInstance();
	}
	
	@JsonIgnore
    private EntityTag tag;
	
	@Override
    @JsonIgnore
    public EntityTag getTag() {
    	return tag;
    }

	@Override
	public long timeToExpire() {
		if(dataExpiration == null) return -1;
		return (dataExpiration.getTimeInMillis() - System.currentTimeMillis()) / 1000;
	}
	
	public void setExpiration(Calendar c) {
		dataExpiration = c;
	}
	
}
