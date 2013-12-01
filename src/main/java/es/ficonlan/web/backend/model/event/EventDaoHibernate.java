/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.model.event;

import es.ficonlan.web.backend.util.dao.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("eventDao")
public class EventDaoHibernate extends GenericDaoHibernate<Event, Integer> implements EventDao {

    @Override
    public Event findEventByName(String name) {
        return (Event) getSession().createCriteria(Event.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
    }
}
