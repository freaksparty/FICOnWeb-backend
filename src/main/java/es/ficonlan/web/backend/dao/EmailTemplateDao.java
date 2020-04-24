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
