package es.ficonlan.web.backend.model.newsitem;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface NewsDao extends GenericDao<NewsItem,Integer> {
	
	public List<NewsItem> getAllNewsItem();
	
	public List<NewsItem> getAllNewsItemFromEvent(int eventId, int startIndex, int cont);
	
	public List<NewsItem> getLastNews(Calendar dateLimit, boolean onlyOutstandingNews); 
	
	public List<NewsItem> getLastNewsFormEvent(int eventId, Calendar dateLimit, boolean onlyOutstandingNews); 
	
	public List<NewsItem> getAllPublishedNewsItemFromEvent(int eventId, int startIndex, int cont);
	
	public long getAllPublishedNewsItemFromEventTam(int eventId);
	
	public long getAllNewsItemFromEventTam(int eventId);
	
}
