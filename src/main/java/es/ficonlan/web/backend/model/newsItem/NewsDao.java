package es.ficonlan.web.backend.model.newsItem;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface NewsDao extends GenericDao<NewsItem,Integer> {
	
	public List<NewsItem> getLastNews(long sessionId, Calendar dateLimit, boolean onlyOutstandingNews); 
	
}
