package es.ficonlan.web.backend.model.newsitem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 */
@Repository("newsDao")
public class NewsDaoHibernate extends GenericDaoHibernate<NewsItem, Integer> implements NewsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getLastNews(Calendar dateLimit, boolean onlyOutstandingNews) {
		
		List<NewsItem> result = getSession().createQuery( "SELECT n " +
                                        				  "FROM NewsItem n " +
                                        				  "WHERE n.publishDate <= current_timestamp() " + 
                                        				  	"AND n.publishDate >= :dateLimit " +
                                                          "ORDER BY n.publishDate DESC" 
														).setDate("dateLimit", dateLimit.getTime()).list();

		
		if (onlyOutstandingNews) {
			List<NewsItem> outstanding = new ArrayList<NewsItem>();
			for (NewsItem n:result){
				Calendar priorityTimeLimit = (Calendar) n.getPublishDate().clone();
				priorityTimeLimit.add(Calendar.HOUR_OF_DAY, n.getPriorityHours());
				if(priorityTimeLimit.compareTo(Calendar.getInstance())>=0) outstanding.add(n);
			}
			return outstanding;
		}
		return result; 
	}
	
}
