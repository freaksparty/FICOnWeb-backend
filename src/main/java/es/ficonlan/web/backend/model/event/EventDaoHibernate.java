/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.model.event;

import java.util.List;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Daniel Gómez Silva
 *
 */
@Repository("eventDao")
public class EventDaoHibernate extends GenericDaoHibernate<Event, Integer> implements EventDao {

    @SuppressWarnings("unchecked")
	public List<Event> searchEventsByName(String name) {
        return getSession().createQuery( "SELECT e " +
        								 "FROM Event e " +
        								 "WHERE LOWER(e.name) LIKE '%'||LOWER(:name)||'%' "
        							   ).setString("name", name).list();
    }
    public Event findEventByName(String name) {
        return (Event) getSession().createCriteria(Event.class)
                .add(Restrictions.eq("name", name)).uniqueResult();
    }
}
