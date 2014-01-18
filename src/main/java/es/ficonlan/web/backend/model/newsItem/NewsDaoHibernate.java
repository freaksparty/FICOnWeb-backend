package es.ficonlan.web.backend.model.newsItem;

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
	public List<NewsItem> getLastNews(long sessionId, Calendar dateLimit, boolean onlyOutstandingNews) {
		if (onlyOutstandingNews) return getSession().createQuery( "SELECT n " +
					  											  "FROM NewsItem " +
					                                              "WHERE n.publishDate >= :dateLimit AND n.publishDate + n.priorityHours*3600000 > current_date() " +
					                                              "ORDER BY n.publishDate DESC" 
			                                                    ).setDate("dateLimit", dateLimit.getTime()).list();
		else return getSession().createQuery( "SELECT n " +
				  							  "FROM NewsItem " +
                                              "WHERE n.publishDate >= :dateLimit " +
                                              "ORDER BY n.publishDate DESC" 
                                            ).setDate("dateLimit", dateLimit.getTime()).list();
	}
	
}
