package es.ficonlan.web.backend.dao;

import java.util.List;

import es.ficonlan.web.backend.entities.EmailTemplate;

/**
 * @author Miguel Ángel Castillo Bellagona

 */
public interface EmailTemplateDao extends GenericDao<EmailTemplate, Integer> {
	public enum TypeEmail {
		/** Participation confirmed and payed (if applies) */
		SPOT_IS_CONFIRMED,
		/** Directly from inscription to pending payment/confirmation */
		PENDING_CONFIRMATION_DIRECT,
		/** Coming from queue to pending payment/confirmation */
		PENDING_CONFIRMATION_FROM_QUEUE,
		/** Staying on queue */
		ON_QUEUE,
		/** The period time for confirm the participant has expired, the participant has loose the spot */
		CONFIRMATION_TIME_EXPIRED};

	public List<EmailTemplate> getAllEmailTemplate();
    
    public EmailTemplate findByName(String name);
    
    public EmailTemplate findByEvent(final int eventId, final TypeEmail type) throws NoSuchFieldException;
}
