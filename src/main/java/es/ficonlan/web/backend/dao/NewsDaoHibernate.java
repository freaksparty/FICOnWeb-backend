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

package es.ficonlan.web.backend.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.NewsItem;

/**
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("newsDao")
public class NewsDaoHibernate extends GenericDaoHibernate<NewsItem, Integer> implements NewsDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getAllNewsItem() {
		return getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n " +
	        	"ORDER BY n.creationDate").list();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getAllNewsItemFromEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n WHERE n.event.eventId = :eventId ORDER BY n." + orderBy +  aux 
		        ).setParameter("eventId", eventId);
		
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}  
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getLastNews(Calendar dateLimit, boolean onlyOutstandingNews) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		List<NewsItem> result = getSession().createQuery( "SELECT n " +
                                        				  "FROM NewsItem n " +
                                        				  "WHERE n.publishDate <= SYSUTCDATETIME() " + 
                                        				  	"AND n.publishDate >= :dateLimit " +
                                                          "ORDER BY n.publishDate DESC" 
														).setDate("dateLimit", dateLimit.getTime()).setParameter("now", now).list();

		
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsItem> getLastNewsFormEvent(int eventId, Calendar dateLimit, boolean onlyOutstandingNews) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		List<NewsItem> result = getSession().createQuery( "SELECT n " +
                                        				  "FROM NewsItem n " +
                                        				  "WHERE n.publishDate <= :now " + 
                                        				  	"AND n.publishDate >= :dateLimit " +
                                        				    "AND n.event.eventId = :eventId" +
                                                          "ORDER BY n.publishDate DESC" 
														).setParameter("eventId", eventId).setParameter("now", now).setDate("dateLimit", dateLimit.getTime()).list();

		
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
	
	@SuppressWarnings("unchecked")
	public List<NewsItem> getPublishedNewsFromEvent(int eventId, int startIndex, int cont) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Query q = getSession().createQuery(
	        	"SELECT n " +
		        "FROM NewsItem n WHERE n.event.eventId = :eventId" 
	        	+ " AND n.publishDate <= :now"
	        	+ " ORDER BY n.publishDate DESC"
		        ).setParameter("eventId", eventId)
				.setParameter("now", now)
				.setFirstResult(startIndex)
				.setMaxResults(cont);
		return q.list();
	}
	
	public long getAllPublishedNewsItemFromEventTam(int eventId) {
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

		return (long) getSession().createQuery(
	        	"SELECT count(n) " +
		        "FROM NewsItem n WHERE n.event.eventId = :eventId" 
	        	+ " AND n.publishDate <= :now"
		        ).setParameter("eventId", eventId).setParameter("now", now).uniqueResult();
	}
	
	public long getAllNewsItemFromEventTam(int eventId) {
		return (long) getSession().createQuery(
	        	"SELECT count(n) " +
		        "FROM NewsItem n WHERE n.event.eventId = :eventId "  +
		        " ORDER BY n.publishDate DESC"
		        ).setParameter("eventId", eventId).uniqueResult();
	}
	
	public Calendar getNextPublicationTime(int eventId) {
		return (Calendar) getSession().createQuery(
				"SELECT min(n.publishDate) FROM NewsItem n " +
				"WHERE n.event.eventId = :eventId AND n.publishDate > current_date"
				).setParameter("eventId", eventId).uniqueResult();
	}
}
