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
