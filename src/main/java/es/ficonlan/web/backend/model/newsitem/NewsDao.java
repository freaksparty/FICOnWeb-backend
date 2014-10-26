package es.ficonlan.web.backend.model.newsitem;

import java.util.Calendar;
import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 * @author Daniel Gómez Silva
 */
public interface NewsDao extends GenericDao<NewsItem,Integer> {
	
	public List<NewsItem> getAllNewsItem();
	
	public List<NewsItem> getLastNews(Calendar dateLimit, boolean onlyOutstandingNews); 
	
}
