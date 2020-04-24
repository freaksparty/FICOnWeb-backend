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
