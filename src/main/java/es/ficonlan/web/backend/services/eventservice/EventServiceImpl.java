package es.ficonlan.web.backend.services.eventservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.dao.ActivityDao;
import es.ficonlan.web.backend.dao.EmailTemplateDao;
import es.ficonlan.web.backend.dao.EventDao;
import es.ficonlan.web.backend.dao.NewsDao;
import es.ficonlan.web.backend.dao.RegistrationDao;
import es.ficonlan.web.backend.dao.SponsorDao;
import es.ficonlan.web.backend.dao.UserDao;
import es.ficonlan.web.backend.entities.Activity;
import es.ficonlan.web.backend.entities.Activity.ActivityType;
import es.ficonlan.web.backend.entities.Email;
import es.ficonlan.web.backend.entities.EmailTemplate;
import es.ficonlan.web.backend.entities.Event;
import es.ficonlan.web.backend.entities.NewsItem;
import es.ficonlan.web.backend.entities.Registration;
import es.ficonlan.web.backend.entities.Registration.RegistrationState;
import es.ficonlan.web.backend.entities.Sponsor;
import es.ficonlan.web.backend.entities.User;
import es.ficonlan.web.backend.model.email.EmailFIFO;
import es.ficonlan.web.backend.model.util.exceptions.InstanceException;
import es.ficonlan.web.backend.model.util.exceptions.ServiceException;
import es.ficonlan.web.backend.model.util.session.SessionManager;
import es.ficonlan.web.backend.output.ShirtData;
import es.ficonlan.web.backend.util.EventRegistrationState;
import es.ficonlan.web.backend.util.RegistrationData;

/**
 * @author David Pereiro
 * @author Daniel Gómez Silva
 * @author Miguel Ángel Castillo Bellagona
 * @author Siro González <xiromoreira>
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {
	
    @Autowired
    private EventDao eventDao;
    
	@Autowired
	private RegistrationDao registrationDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private NewsDao newsDao;
    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SponsorDao sponsorDao;
	
	@Autowired
	private EmailTemplateDao emailTemplateDao;

    @Override
    @Transactional
	public Event createEvent(Event event) throws ServiceException {
		if(event.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(event.getStartDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"startDate");
		if(event.getEndDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"endDate");
		if(event.getRegistrationOpenDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationOpenDate");
		if(event.getRegistrationCloseDate()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"registrationCloseDate");
		if(eventDao.findEventByName(event.getName())!=null) throw new ServiceException(ServiceException.DUPLICATED_FIELD,"name");
    	eventDao.save(event);
    	return event;
	}
    
    @Override
    @Transactional
    public void removeEvent(String sessionId, int eventId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try
		{
			eventDao.remove(eventId);
		}
		catch (InstanceException e)
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
    }
    
    @Override
    @Transactional(readOnly = true)
    public Event getEvent(int eventId) throws ServiceException {
    	try
		{
			return eventDao.find(eventId);
		}
		catch (InstanceException e)
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
    }
    
    /*@Override
    @Transactional(readOnly = true)
    public String getEventRules(String sessionId, int eventId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getEventRules")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try{
    		Event event = eventDao.find(eventId);
    		return event.getNormas();
    	} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
    }*/
    
    @Override
    @Transactional(readOnly = true)
    public boolean eventIsOpen(int eventId) throws ServiceException {
    	try{
    		Event event = eventDao.find(eventId);
    		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    		if(now.after(event.getRegistrationOpenDate()) && now.before(event.getRegistrationCloseDate())) return true;
    		else return false;
    	} catch (InstanceException e) {
    		throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
    	}
    }

    @Override
    @Transactional
	public Event changeEventData(int eventId, Event eventData) throws ServiceException {
    	try{
    		Event event = eventDao.find(eventId);
    		int oldNumParticipants = event.getNumParticipants();
    		if(eventData.getName()!=null) event.setName(eventData.getName());
    		if(eventData.getDescription()!=null) event.setDescription(eventData.getDescription());
    	    if(eventData.getNumParticipants()>0) event.setNumParticipants(eventData.getNumParticipants());
    	    if(eventData.getMinimunAge()>=0) event.setMinimunAge(eventData.getMinimunAge());
    	    if(eventData.getPrice()>=0) event.setPrice(eventData.getPrice());
    		if(eventData.getStartDate()!=null) event.setStartDate(eventData.getStartDate());
    		if(eventData.getEndDate()!=null) event.setEndDate(eventData.getEndDate());
    		if(eventData.getRegistrationOpenDate()!=null) event.setRegistrationOpenDate(eventData.getRegistrationOpenDate());
    		if(eventData.getRegistrationCloseDate()!=null) event.setRegistrationCloseDate(eventData.getRegistrationCloseDate());
    		if(eventData.getNormas()!=null) event.setNormas(eventData.getNormas());
        	eventDao.save(event);
        	if(eventData.getNumParticipants()>oldNumParticipants) eventNumParticipantsChanged(eventId);
        	
        	return event;
    	} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}

    @Override
	@Transactional
	public Registration addParticipantToEvent(String sessionId, int userId, int eventId) throws ServiceException {
		if (!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "addParticipantToEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try{
    		User user = userDao.find(userId);
  	
    		Event event = eventDao.find(eventId);
    		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    		if(!((now.after(event.getRegistrationOpenDate()) && now.before(event.getRegistrationCloseDate())))) throw new ServiceException(9,"addParticipantToEvent");
    		//System.out.println(now.getTime()); System.out.println(event.getRegistrationOpenDate().getTime()); System.out.println(event.getRegistrationCloseDate().getTime());
    		Calendar agedif = (Calendar) user.getDob().clone();
    			
    		agedif.add(Calendar.YEAR, event.getMinimunAge());
    		
    		if(agedif.after(now)) throw new ServiceException(ServiceException.YOUR_ARE_TOO_YOUNG);
    		
    		Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    		if (registration==null) registration = new Registration(user, event);
    		else throw new  ServiceException(ServiceException.DUPLICATED_FIELD,"Registration");
    		
    		int currentParticipants = registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.registered) +
    								  registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.paid);
    		int queueParticipants =   registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.inQueue);
    		
    		Hashtable<String,String> tabla = new Hashtable<String,String>();
    		tabla.put("#nombreusuario", user.getName());
    		tabla.put("#loginusuario", user.getLogin());
    		tabla.put("#dniusuario", user.getDni());
    		tabla.put("#numerotelefonousuario", user.getPhoneNumber());
    		tabla.put("#tallacamisetausuario", user.getShirtSize());
    		tabla.put("#nombreevento", event.getName());
    		tabla.put("#fechainicioevento", event.getStartDate().toString());
    		tabla.put("#fechafinevento", event.getEndDate().toString());
    		tabla.put("#edadminima", Integer.toString(event.getMinimunAge()));
    		tabla.put("#precio", Integer.toString(event.getPrice()));
 
    		if(currentParticipants>=event.getNumParticipants())
    		{
    			registration.setPlaceOnQueue(1 +  queueParticipants); //FIXME SIN TESTEAR
    			registration.setState(RegistrationState.inQueue);
    			
    			tabla.put("#plazaencola", Integer.toString(registration.getPlaceOnQueue()));
    			tabla.put("#plazaenevento", "");
    			
    			//FIXME: Mandar correo electrónico
    			if(event.getOnQueueTemplate()!=null)
    			{
    				Email email = event.getOnQueueTemplate().generateEmail(user, tabla);
    				EmailFIFO.addEmailToQueue(email);
    			}
    			
    		}
    		else if(user.isInBlackList())
    		{
    			registration.setPlaceOnQueue(event.getNumParticipants() + 200); //FIXME SIN TESTEAR
    			registration.setState(RegistrationState.inQueue);
    			
    			//FIXME: Mandar correo electrónico
    			
    			tabla.put("#plazaencola", "200");
    			tabla.put("#plazaenevento", "");
    			
    			//FIXME: Mandar correo electrónico
    			if(event.getOnQueueTemplate()!=null)
    			{
    				Email email = event.getOnQueueTemplate().generateEmail(user, tabla);
    				EmailFIFO.addEmailToQueue(email);
    			}
    		}
    		else {
    			registration.setState(RegistrationState.registered);
    			registration.setPlace(currentParticipants + 1); //FIXME SIN TESTEAR

            	//FIXME: Mandar correo electrónico
    			
    			tabla.put("#plazaencola", "");
    			tabla.put("#plazaenevento", Integer.toString(registration.getPlace()));
    			
    			//FIXME: Mandar correo electrónico
    			if(event.getOutstandingTemplate()!=null)
    			{
    				Email email = event.getOutstandingTemplate().generateEmail(user, tabla);
    				//email.sendMailThread();
    				//email.sendMail();
    				EmailFIFO.addEmailToQueue(email);
    			}
    		}
    		
    		registrationDao.save(registration);
    		return registration;
    		
    	} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		
	}

    @Override
	@Transactional
	public void removeParticipantFromEvent(String sessionId, int userId, int eventId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeParticipantFromEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");

    	User user = registration.getUser();
    	Event event = registration.getEvent();

		Hashtable<String,String> tabla = new Hashtable<String,String>();
		tabla.put("#nombreusuario", user.getName());
		tabla.put("#loginusuario", user.getLogin());
		tabla.put("#dniusuario", user.getDni());
		tabla.put("#numerotelefonousuario", user.getPhoneNumber());
		tabla.put("#tallacamisetausuario", user.getShirtSize());
		tabla.put("#nombreevento", event.getName());
		tabla.put("#fechainicioevento", event.getStartDate().toString());
		tabla.put("#fechafinevento", event.getEndDate().toString());
		tabla.put("#edadminima", Integer.toString(event.getMinimunAge()));
		tabla.put("#precio", Integer.toString(event.getPrice()));
    	
        try {
			//FIXME: Mandar correo elecrónico if registration.getState()==registered Mandar correo electrónico registration.User()
			if ((registration.getState()==RegistrationState.registered) || (registration.getState()==RegistrationState.paid)) {
				
				tabla.put("#plazaencola", "");
    			tabla.put("#plazaenevento", "");
    			
    			if((event.getOutOfDateTemplate()!=null) && (registration.getState()==RegistrationState.registered))
    			{
    				Email email = event.getOutOfDateTemplate().generateEmail(user, tabla);
    				//email.sendMailThread();
    				//email.sendMail();
    				EmailFIFO.addEmailToQueue(email);
    			}
    			
    			Registration firstInQueue = registrationDao.getFirstInQueue(eventId);
    			 
    		    if(firstInQueue!=null) {
    		        firstInQueue.setState(RegistrationState.registered);
    		        firstInQueue.setPlace(registration.getPlace());
    		        	
    		        //FIXME: Mandar correo electrónico
    		        	
    		        Hashtable<String,String> tabla2 = new Hashtable<String,String>();
    		    	tabla2.put("#nombreusuario", firstInQueue.getUser().getName());
    		    	tabla2.put("#loginusuario", firstInQueue.getUser().getLogin());
    		    	tabla2.put("#dniusuario", firstInQueue.getUser().getDni());
    		    	tabla2.put("#numerotelefonousuario", firstInQueue.getUser().getPhoneNumber());
    		    	tabla2.put("#tallacamisetausuario", firstInQueue.getUser().getShirtSize());
    		    	tabla2.put("#nombreevento", event.getName());
    		    	tabla2.put("#fechainicioevento", event.getStartDate().toString());
    		    	tabla2.put("#fechafinevento", event.getEndDate().toString());
    		    	tabla2.put("#edadminima", Integer.toString(event.getMinimunAge()));
    		    	tabla2.put("#precio", Integer.toString(event.getPrice()));
    		        	
    		        tabla2.put("#plazaencola", "");
    				tabla2.put("#plazaenevento", Integer.toString(registration.getPlace()));
    					
    				if(event.getFromQueueToOutstanding()!=null)
    				{
    					Email email = event.getFromQueueToOutstanding().generateEmail(firstInQueue.getUser(), tabla2);
    					//email.sendMail();
    					//email.sendMailThread();
    					EmailFIFO.addEmailToQueue(email);
    				}
    				registrationDao.save(firstInQueue);
    		       }
			}
			
			registrationDao.remove(registration.getRegistrationId());
			
		} catch (InstanceException e) {
			 throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
		}
	}
	
    @Override
	@Transactional
	public void setPaid(String sessionId, int userId, int eventId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "setPaid")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
    	if (registration.getState()!=RegistrationState.registered) throw new ServiceException(ServiceException.OTHER,"Only registered");
    	registration.setPaidDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    	registration.setPaid(true);
    	registration.setState(RegistrationState.paid);
		
    	//FIXME: Mandar correo electrónico
		
    	User user = registration.getUser();
    	Event event = registration.getEvent();

		if(event.getSetPaidTemplate()!=null)
		{
			Hashtable<String,String> tabla = new Hashtable<String,String>();
			tabla.put("#nombreusuario", user.getName());
			tabla.put("#loginusuario", user.getLogin());
			tabla.put("#dniusuario", user.getDni());
			tabla.put("#numerotelefonousuario", user.getPhoneNumber());
			tabla.put("#tallacamisetausuario", user.getShirtSize());
			tabla.put("#nombreevento", event.getName());
			tabla.put("#fechainicioevento", event.getStartDate().toString());
			tabla.put("#fechafinevento", event.getEndDate().toString());
			
			tabla.put("#plazaencola", "");
			tabla.put("#plazaenevento", Integer.toString(registration.getPlace()));
			
			tabla.put("#edadminima", Integer.toString(event.getMinimunAge()));
			
			Email email = event.getSetPaidTemplate().generateEmail(user, tabla);
			//email.sendMailThread();
			//email.sendMail();
			EmailFIFO.addEmailToQueue(email);
		}
		registrationDao.save(registration);
	}
	
    @Override
	@Transactional(readOnly = true)
	public Registration getRegistration(String sessionId, int userId, int eventId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "getRegistration")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try {
			Event event = eventDao.find(eventId);
			
			Registration registration =  registrationDao.findByUserAndEvent(userId, eventId);
			if(registration == null) throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
			if(registration.getState()==RegistrationState.inQueue) {
				int queueParticipants = registrationDao.geNumRegistrationsBeforeDate(event.getEventId(),RegistrationState.inQueue,registration.getRegistrationDate());
				registration.setPlaceOnQueue(queueParticipants + 1);
			}
			else registration.setPlaceOnQueue(0);
			
			return registration;
		}
		catch (InstanceException e)
		{
			return null;
		}
	}

    @Override
	@Transactional(readOnly = true)
    public void sendRegistrationMail(String sessionId, int userId, int eventId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "sendRegistrationMail")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try {
			User user = userDao.find(userId);
			Event event = eventDao.find(eventId);
			Registration registration =  getRegistration(sessionId,userId,userId);

			Hashtable<String,String> tabla = new Hashtable<String,String>();
			tabla.put("#nombreusuario", user.getName());
			tabla.put("#loginusuario", user.getLogin());
			tabla.put("#dniusuario", user.getDni());
			tabla.put("#numerotelefonousuario", user.getPhoneNumber());
			tabla.put("#tallacamisetausuario", user.getShirtSize());
			tabla.put("#nombreevento", event.getName());
			tabla.put("#fechainicioevento", event.getStartDate().toString());
			tabla.put("#fechafinevento", event.getEndDate().toString());
			tabla.put("#edadminima", Integer.toString(event.getMinimunAge()));
			tabla.put("#precio", Integer.toString(event.getPrice()));
			
			tabla.put("#plazaencola", Integer.toString(registration.getPlaceOnQueue()));
			tabla.put("#plazaenevento", Integer.toString(registration.getPlace()));
			
			if(registration.getState()==RegistrationState.inQueue) {
				if(event.getOnQueueTemplate()!=null)
    			{
    				Email email = event.getOnQueueTemplate().generateEmail(user, tabla);
    				//email.sendMailThread();
    				//email.sendMail();
    				EmailFIFO.addEmailToQueue(email);
    			}
			}
			else
			if(registration.getState()==RegistrationState.paid) {
				if(event.getSetPaidTemplate()!=null)
				{
					Email email = event.getSetPaidTemplate().generateEmail(user, tabla);
					//email.sendMailThread();
					//email.sendMail();
					EmailFIFO.addEmailToQueue(email);
				}
			}
			else
			if(registration.getState()==RegistrationState.registered) {
				if(event.getOutstandingTemplate()!=null)
				{
					Email email = event.getOutstandingTemplate().generateEmail(user, tabla);
					//email.sendMailThread();
					//email.sendMail();
					EmailFIFO.addEmailToQueue(email);
				}
			}
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
      	
    }
    
	/**
	 * This method doesn,t check constraints. And dont send e-mails
	 * Use only in exceptional circumstances. Otherways use "setPaid" method.
	 */
    @Override
	@Transactional
	public void changeRegistrationState(String sessionId, int userId, int eventId, RegistrationState state) throws ServiceException {
		if(state==null) throw new  ServiceException(ServiceException.MISSING_FIELD,"state");
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeRegistrationState")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	Registration registration = registrationDao.findByUserAndEvent(userId, eventId);
    	if (registration==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Registration");
    	if(state==RegistrationState.paid){
    		registration.setPaidDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    		registration.setPaid(true);
    	}
    	registration.setState(state);
		registrationDao.save(registration);
	}
    
    @Override
	@Transactional
    public void eventNumParticipantsChanged(int eventId) throws ServiceException {
    	
		Event event = null;
		try
		{
			event = eventDao.find(eventId);
		}
		catch (InstanceException e)
		{
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		
        if (!eventIsOpen(eventId)) return;

		int currentParticipants = registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.registered) +
				  registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.paid);
		int queueParticipants = registrationDao.geNumRegistrations(event.getEventId(),RegistrationState.inQueue);

		int bucle = Math.min(event.getNumParticipants() - currentParticipants,queueParticipants);
		
		for (int i=0; i <bucle; i++)
		{
			Registration firstInQueue = registrationDao.getFirstInQueue(eventId);
	        if(firstInQueue!=null)
	        {
	        	firstInQueue.setState(RegistrationState.registered);
	        	firstInQueue.setPlace(currentParticipants+1+i);
	        	//FIXME: Mandar correo electrónico
	        	
	        	User user = firstInQueue.getUser();

				if(event.getFromQueueToOutstanding()!=null)
				{
					Hashtable<String,String> tabla = new Hashtable<String,String>();
		    		tabla.put("#nombreusuario", user.getName());
		    		tabla.put("#loginusuario", user.getLogin());
		    		tabla.put("#dniusuario", user.getDni());
		    		tabla.put("#numerotelefonousuario", user.getPhoneNumber());
		    		tabla.put("#tallacamisetausuario", user.getShirtSize());
		    		tabla.put("#nombreevento", event.getName());
		    		tabla.put("#fechainicioevento", event.getStartDate().toString());
		    		tabla.put("#fechafinevento", event.getEndDate().toString());
		    		
		    		tabla.put("#plazaencola", "");
		    		tabla.put("#plazaenevento", Integer.toString(firstInQueue.getPlace()));
					Email email = event.getFromQueueToOutstanding().generateEmail(user, tabla);
					//email.sendMailThread();
					EmailFIFO.addEmailToQueue(email);
				}
				registrationDao.save(firstInQueue);
	        }
		}
    }
    
    @Override
   	@Transactional(readOnly = true)
	public List<RegistrationData> getRegistrationByEvent(int eventId, RegistrationState state, int startindex, int maxResults, String orderBy, boolean desc) throws ServiceException {
		List<Registration> list = registrationDao.getRegistrationByEvent(eventId, state, startindex, maxResults, orderBy, desc);
		
		if(orderBy=="placeOnQueue") {
			if(desc==false) list = list.stream().sorted((e1, e2) -> Integer.compare(e1.getPlaceOnQueue(),e2.getPlaceOnQueue())).collect(Collectors.toList());
			else list = list.stream().sorted((e1, e2) -> Integer.compare(e2.getPlaceOnQueue(),e1.getPlaceOnQueue())).collect(Collectors.toList());
		}
		
		List<RegistrationData> rd = new ArrayList<RegistrationData>();
		Iterator<Registration> i = list.iterator();
		while(i.hasNext()) {
			Registration r = i.next();
			int placeQ = 0;
			if(r.getState()==RegistrationState.inQueue) placeQ = registrationDao.geNumRegistrationsBeforeDate(r.getEvent().getEventId(),RegistrationState.inQueue,r.getRegistrationDate()) + 1;
			rd.add(new RegistrationData(r.getUser().getLogin(),r.getUser().getDni(),r.getRegistrationId(),r.getUser().getUserId(),r.getEvent().getEventId(),r.getState(),r.getRegistrationDate(),r.getPaidDate(),r.isPaid(),r.getPlace(),placeQ));
			
		}
		return rd;
    }
    
    @Override
	@Transactional(readOnly = true)
    public long getRegistrationByEventTAM(int eventId, RegistrationState state) throws ServiceException {
		return registrationDao.getRegistrationByEventTAM(eventId, state);
    }

    @Override
	@Transactional(readOnly = true)
    public List<ShirtData> getShirtSizes(int eventId) throws ServiceException {
		return registrationDao.getShirtSizesPaid(eventId);
    }
    
    @Override
	@Transactional(readOnly = true)
	public EventRegistrationState getEventRegistrationState(String sessionId, int eventId, int userId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getEventRegistrationState")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		try {
			Event event = eventDao.find(eventId);
		
			Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			boolean open = (now.after(event.getRegistrationOpenDate()) && now.before(event.getRegistrationCloseDate()));

			Registration r = null;
			try {
				r = getRegistration(sessionId,userId,eventId);
			}
			catch (ServiceException e) {}
			if(r!=null) {
				RegistrationState state = r.getState();
				int place = 0;
				if(state==RegistrationState.paid) 		place = r.getPlace();
				if(state==RegistrationState.registered) place = r.getPlace();
				if(state==RegistrationState.inQueue) 	place = registrationDao.geNumRegistrationsBeforeDate(event.getEventId(),RegistrationState.inQueue,r.getRegistrationDate()) + 1;
				return new EventRegistrationState(open,state,place);
			}
			else return new EventRegistrationState(open,null,0);

		}
		catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
    	
    }
    
    @Override
	@Transactional(readOnly = true)
	public List<Event> getAllEvents(String sessionId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllEvents")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		return eventDao.getAllEvents();
		
	}

    @Override
    @Transactional(readOnly = true)
    public List<Event> findEventByName(String sessionId, String name) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "findEventByName")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return eventDao.searchEventsByName(name);
    }
   
    @Override
    @Transactional
    public Activity addActivity(int eventId, Activity activity) throws ServiceException {
    	Event event;
    	try {
    		event = eventDao.find(eventId);
    		activity.setEvent(event);
    		if(activity.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
    		if(activity.getNumParticipants()<1) throw new ServiceException(ServiceException.INCORRECT_FIELD,"numParticipants");
    		if(activity.getNumParticipants()<1) throw new ServiceException(ServiceException.INCORRECT_FIELD,"numParticipants");
    		if(activity.getStartDate()==null) activity.setStartDate(activity.getEvent().getStartDate());
    		if(activity.getStartDate().compareTo(activity.getEvent().getStartDate())<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"startDate");
    		if(activity.getEndDate()==null) activity.setEndDate(activity.getEvent().getEndDate());
    		if(activity.getEndDate().compareTo(activity.getEvent().getEndDate())>0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"endDate");
    		if(activity.getRegDateOpen()==null) activity.setRegDateOpen(activity.getEvent().getStartDate());
    		if(activity.getRegDateClose()==null) activity.setRegDateClose(activity.getEvent().getEndDate());
    		activityDao.save(activity);
    		return activity;
    	} catch (InstanceException e) {
    		throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
    	}
    }

    @Override
    @Transactional
	public void removeActivity(String sessionId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			activityDao.remove(activityId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}
	}
    
    @Override
    @Transactional
	public Activity changeActivityData(int activityId, Activity activityData) throws ServiceException {
		try {
			Activity activity = activityDao.find(activityId);
			activity.setActivityId(activityId);
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getName()!=null) activity.setName(activityData.getName());
			if(activityData.getDescription()!=null) activity.setDescription(activityData.getDescription());
			if(activityData.getNumParticipants()>0) activity.setNumParticipants(activityData.getNumParticipants());

			if(activityData.getType()!=null) activity.setType(activityData.getType());
			activity.setOficial(activityData.isOficial());
			if(activityData.getStartDate()!=null){
				if(activityData.getStartDate().compareTo(activity.getEvent().getStartDate())<0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"startDate");
				activity.setStartDate(activityData.getStartDate());
			}
			if(activityData.getEndDate()!=null){
				if(activityData.getEndDate().compareTo(activity.getEvent().getEndDate())>0) throw new ServiceException(ServiceException.INCORRECT_FIELD,"endDate");
				activity.setEndDate(activityData.getEndDate());
			}
			if(activityData.getRegDateOpen()!=null) activity.setRegDateOpen(activityData.getRegDateOpen());
			if(activityData.getRegDateClose()!=null) activity.setRegDateClose(activityData.getRegDateClose());
			activityDao.save(activity);
			return activity;
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}
	}
    
    @Override
    @Transactional(readOnly = true)
    public Activity getActivity(int activityId) throws ServiceException {
		try {
			return activityDao.find(activityId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND, "Activity");
		}
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Activity> getAllActivities(String sessionId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllActivities")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		return activityDao.getAllActivity();
    }

    @Override
    @Transactional(readOnly = true)
	public List<Activity> getActivitiesByEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc, ActivityType type) throws ServiceException {
    	try {
			eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		return activityDao.findActivitiesByEvent(eventId, type , startIndex, cont, orderBy, desc);
	}
    
    @Override
    @Transactional(readOnly = true)
    public long getActivitiesByEventTAM(int eventId, ActivityType type) throws ServiceException {
		try {
			eventDao.find(eventId);
		} catch (InstanceException e) {
			throw new ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
		return activityDao.findActivitiesByEventTAM(eventId, type);
    }

    @Override
    @Transactional
	public void addParticipantToActivity(String sessionId, int userId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "addParticipantToActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if(registrationDao.findByUserAndEvent(user.getUserId(), activity.getEvent().getEventId())==null) throw new ServiceException(ServiceException.USER_NOT_REGISTERED_IN_EVENT,"userId");
			if(activity.getParticipants().size()>=activity.getNumParticipants()) throw new ServiceException(ServiceException.MAX_NUM_PARTICIPANTS_REACHED,"Activity(Id="+activity.getActivityId()+")");
			if (!activity.getParticipants().contains(user)) activity.getParticipants().add(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}
	}

    @Override
    @Transactional
	public void removeParticipantFromActivity(String sessionId, int userId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), userId, "removeParticipantFromActivity")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Activity activity = activityDao.find(activityId);
			User user = userDao.find(userId);
			if (activity.getParticipants().contains(user)) activity.getParticipants().remove(user);
			activityDao.save(activity);
		} catch (InstanceException e) {
			if (e.getClassName().contentEquals("User")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"User");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Activity");
		}
	}
    
    @Override
    @Transactional(readOnly = true)
	public List<User> getActivityParticipants(String sessionId, int activityId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getActivityParticipants")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	return activityDao.getParticipants(activityId);
	}


    @Override
    @Transactional
	public NewsItem addNews(String sessionId, int eventId, NewsItem newsItem) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Event event = eventDao.find(eventId);
			
			newsItem.setEvent(event);
			if(newsItem.getEvent()==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			if(newsItem.getTitle()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"title");
			if(newsItem.getContent()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"Content");

			newsItem.setPublisher(userDao.find(SessionManager.getSession(sessionId).getUserId()));
			newsItem.setCreationDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
			if(newsItem.getPublishDate()==null) newsItem.setPublishDate(newsItem.getCreationDate());
			newsDao.save(newsItem);
			return newsItem;
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
	}

    @Override
    @Transactional
	public void changeNewsData(String sessionId, int newsItemId, NewsItem newsData) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "changeNewsData")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			NewsItem news = newsDao.find(newsItemId);
			newsData.setNewsItemId(newsItemId);
			if(newsData.getTitle()!=null) news.setTitle(newsData.getTitle());
			if(newsData.getPublishDate()!=null) news.setPublishDate(newsData.getPublishDate());
			if(newsData.getContent()!=null) news.setContent(newsData.getContent());
			if(newsData.getPriorityHours()!=0) news.setPriorityHours(newsData.getPriorityHours());
			newsDao.save(news);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}
	}
    
    @Override
    @Transactional
    public NewsItem getNewsItem(String sessionId, int newsItemId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getNewsItem")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	
		try
		{
			return newsDao.find(newsItemId);
		}
		catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NewsItem> getAllNewsItem(String sessionId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getAllNewsItem")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	
    	return newsDao.getAllNewsItem();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<NewsItem> getAllNewsItemFormEvent(int eventId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException {    	
		return newsDao.getAllNewsItemFromEvent(eventId,startIndex,cont,orderBy,desc);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getAllNewsItemFromEventTam(int eventId) throws ServiceException {
    	return newsDao.getAllPublishedNewsItemFromEventTam(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsItem>getPublishedNewsForEvent(int eventId, int startIndex, int cont) throws ServiceException {
		return newsDao.getPublishedNewsFromEvent(eventId,startIndex,cont);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countPublishedNewsFromEvent(int eventId) throws ServiceException {
    	return newsDao.getAllPublishedNewsItemFromEventTam(eventId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Calendar nextNewsUpdate(int eventId) throws ServiceException {
    	return newsDao.getNextPublicationTime(eventId);
    }
      
    @Override
    @Transactional(readOnly = true)
	public List<NewsItem> getLastNews(String sessionId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getLastNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	if(dateLimit==null) throw new ServiceException(ServiceException.MISSING_FIELD,"limitDate");
		return newsDao.getLastNews(dateLimit, onlyOutstandingNews);
	}
    
    @Override
    @Transactional(readOnly = true)
    public List<NewsItem> getLastNewsFromEvent(String sessionId, int eventId, Calendar dateLimit, boolean onlyOutstandingNews) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getLastNewsFromEvent")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	if(dateLimit==null) throw new ServiceException(ServiceException.MISSING_FIELD,"limitDate");
		return newsDao.getLastNewsFormEvent(eventId, dateLimit, onlyOutstandingNews);
    }

    @Override
    @Transactional
	public void removeNews(String sessionId, int newsItemId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeNews")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try {
			newsDao.remove(newsItemId);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"NewsItem");
		}
	}
    
    @Override
    @Transactional
    public Sponsor addSponsor(String sessionId, Sponsor sponsor) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addSponsor")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		
		if(sponsor.getEvent()==null) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		if(sponsor.getName()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"name");
		if(sponsor.getImageurl()==null) throw new ServiceException(ServiceException.MISSING_FIELD,"url");
		sponsorDao.save(sponsor);
		return sponsor;
    }
    
    @Override
    @Transactional
    public Sponsor addSponsor(String sessionId, int eventId, Sponsor sponsor) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "addSponsor")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try {
			Event event = eventDao.find(eventId);
			sponsor.setEvent(event);
			return addSponsor(sessionId,sponsor);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
		}
    }
    
    @Override
    @Transactional
    public void removeSponsor(String sessionId, int sponsorId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "removeSponsor")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	try {
    		sponsorDao.remove(sponsorId);
		} catch (InstanceException e) {
			throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Sponsor");
		}
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sponsor> getSponsors(String sessionId, int startIndex, int cont, String orderBy, boolean desc) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getSponsors")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	return sponsorDao.getAll(startIndex,cont,orderBy,desc);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getSponsorsTAM(String sessionId) throws ServiceException {
    	if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "getSponsorsTAM")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
    	return sponsorDao.getAllTAM();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sponsor> getSponsorsByEvent(int eventId) throws ServiceException {
    	return sponsorDao.getByEvent(eventId);
    }

	@Override
	public void setPaidTemplate(String sessionId, int eventId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "setPaidTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try
		{
			Event event = eventDao.find(eventId);
			EmailTemplate emailtemplate = emailTemplateDao.find(emailTemplateId);
			event.setSetPaidTemplate(emailtemplate);
			eventDao.save(event);
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void onQueueTemplate(String sessionId, int eventId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "onQueueTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try
		{
			Event event = eventDao.find(eventId);
			EmailTemplate emailtemplate = emailTemplateDao.find(emailTemplateId);
			event.setOnQueueTemplate(emailtemplate);
			eventDao.save(event);
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void outstandingTemplate(String sessionId, int eventId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "outstandingTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try
		{
			Event event = eventDao.find(eventId);
			EmailTemplate emailtemplate = emailTemplateDao.find(emailTemplateId);
			event.setOutstandingTemplate(emailtemplate);
			eventDao.save(event);
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void outOfDateTemplate(String sessionId, int eventId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "outOfDateTemplate")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try
		{
			Event event = eventDao.find(eventId);
			EmailTemplate emailtemplate = emailTemplateDao.find(emailTemplateId);
			event.setOutOfDateTemplate(emailtemplate);
			eventDao.save(event);
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	@Override
	public void fromQueueToOutstanding(String sessionId, int eventId, int emailTemplateId) throws ServiceException {
		if(!SessionManager.exists(sessionId)) throw new ServiceException(ServiceException.INVALID_SESSION);
		if(!SessionManager.checkPermissions(SessionManager.getSession(sessionId), "fromQueueToOutstanding")) throw new ServiceException(ServiceException.PERMISSION_DENIED);
		try
		{
			Event event = eventDao.find(eventId);
			EmailTemplate emailtemplate = emailTemplateDao.find(emailTemplateId);
			event.setFromQueueToOutstanding(emailtemplate);
			eventDao.save(event);
		}
		catch (InstanceException e) {
			if (e.getClassName().contentEquals("Event")) throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"Event");
			else throw new  ServiceException(ServiceException.INSTANCE_NOT_FOUND,"EmailTemplate");
		}
	}

	/*@Override
	public String getEventRules(String sessionId, int eventId) throws ServiceException {
		// TODO Apéndice de método generado automáticamente
		return null;
	}*/

	@Override
	public List<Activity> getActivitiesByEvent(int eventId, ActivityType type) {
		return activityDao.findActivitiesByEvent(eventId, type);
	}

}
