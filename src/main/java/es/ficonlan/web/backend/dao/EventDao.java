/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.Event;

/**
 * @author Daniel Gómez Silva
 * @author david
 */
public interface EventDao extends GenericDao<Event, Integer> {
	
	public List<Event> getAllEvents();

    public List<Event> searchEventsByName(String name);
    
    public Event findEventByName(String name);

}
