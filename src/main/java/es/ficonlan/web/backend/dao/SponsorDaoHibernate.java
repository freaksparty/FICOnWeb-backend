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

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.Sponsor;

/**
 * @author Miguel Ángel Castillo Bellagona
 * @version 1.0
 */
@Repository("sponsorDao")
public class SponsorDaoHibernate extends GenericDaoHibernate<Sponsor,Integer> implements SponsorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getAll(int startIndex, int cont, String orderBy, boolean desc) {
		String aux = " ";
		if(desc) aux=" DESC";
		Query query = getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s ORDER BY s." + orderBy +  aux);
		
		if(cont<1) return query.list();
		else return query.setFirstResult(startIndex).setMaxResults(cont).list();
	}
	
	@Override
	public long getAllTAM() {
		return (long) getSession().createQuery(
				"SELECT s FROM Sponsor s"
		        ).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sponsor> getByEvent(int eventId) {
		return getSession().createQuery(
	        	"SELECT s " +
		        "FROM Sponsor s " + 
	        	"WHERE s.event.eventId = :eventId").setParameter("eventId", eventId).list();
	}

}
