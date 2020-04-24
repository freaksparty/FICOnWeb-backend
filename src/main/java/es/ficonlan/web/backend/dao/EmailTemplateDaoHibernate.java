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

import es.ficonlan.web.backend.entities.EmailTemplate;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Repository("emailTemplateDao")
public class EmailTemplateDaoHibernate extends GenericDaoHibernate<EmailTemplate, Integer> implements EmailTemplateDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailTemplate> getAllEmailTemplate() {
		return getSession().createQuery( "SELECT e " +
				 "FROM EmailTemplate e ").list();
	}
	
	@Override
	public EmailTemplate findByName(String name) {
		return (EmailTemplate) getSession()
				.createQuery("SELECT e FROM EmailTemplate e WHERE e.name = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	@Override
	public EmailTemplate findByEvent(final int eventId, final TypeEmail type) throws NoSuchFieldException {
		String sql;
		switch(type) {
			case SPOT_IS_CONFIRMED:
				sql = "SELECT ev.setPaidTemplate";
				break;
			case ON_QUEUE:
				sql = "SELECT ev.onQueueTemplate";
				break;
			case CONFIRMATION_TIME_EXPIRED:
				sql = "SELECT ev.outOfDateTemplate";
				break;
			case PENDING_CONFIRMATION_DIRECT:
				sql = "SELECT ev.outstandingTemplate";
				break;
			case PENDING_CONFIRMATION_FROM_QUEUE:
				sql = "SELECT ev.fromQueueToOutstanding";
				break;
			default:
				throw new NoSuchFieldException("Not found email template for type " + type.toString());
		}
		sql += " FROM Event ev WHERE ev.eventId = :eventId";
		
		Query query = getSession().createQuery(sql).setParameter("eventId", eventId);
		return (EmailTemplate) query.uniqueResult();
	}

}
