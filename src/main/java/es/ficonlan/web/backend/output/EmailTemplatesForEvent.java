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

package es.ficonlan.web.backend.output;

public class EmailTemplatesForEvent {
	
	/** Spot is payed or confirmed to assist */
	public String contentSpotConfirmed;
	public String subjectSpotConfirmed;
	public long idSpotConfirmed;
	
	/** Directly from inscription to pending payment/confirmation */
	public String contentPendingConfirmationDirect;
	public String subjectPendingConfirmationDirect;
	public long idPendingConfirmationDirect;
	
	/** Comming from queue to pending payment/confirmation */
	public String contentPendingConfirmationFromQueue;
	public String subjectPendingConfirmationFromQueue;
	public long idPendingConfirmationFromQueue;
	
	public String contentOnQueue;
	public String subjectOnQueue;
	public long idOnQueue;
	
	public String contentConfirmationPeriodExpired;
	public String subjectConfirmationPeriodExpired;
	public long idConfirmationPeriodExpired;

}
