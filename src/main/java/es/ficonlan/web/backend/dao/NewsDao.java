package es.ficonlan.web.backend.dao;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.entities.NewsItem;

/**
 * @author Daniel Gómez Silva
 * @author Siro González <xiromoreira>
 */
public interface NewsDao extends GenericDao<NewsItem,Integer> {
	
	public List<NewsItem> getAllNewsItem();
	
	public List<NewsItem> getAllNewsItemFromEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc);
	
	public List<NewsItem> getLastNews(Calendar dateLimit, boolean onlyOutstandingNews); 
	
	public List<NewsItem> getLastNewsFormEvent(int eventId, Calendar dateLimit, boolean onlyOutstandingNews); 
	
	public List<NewsItem> getPublishedNewsFromEvent(int eventId, int startIndex, int cont);
	
	public long getAllPublishedNewsItemFromEventTam(int eventId);
	
	public long getAllNewsItemFromEventTam(int eventId);
	
	public Calendar getNextPublicationTime(int eventId);
	
}
