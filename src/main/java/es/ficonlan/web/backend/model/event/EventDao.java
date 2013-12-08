/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ficonlan.web.backend.model.event;

import es.ficonlan.web.backend.model.util.dao.GenericDao;

/**
 *
 * @author david
 */
public interface EventDao extends GenericDao<Event, Integer> {

    public Event findEventByName(String name);

}
