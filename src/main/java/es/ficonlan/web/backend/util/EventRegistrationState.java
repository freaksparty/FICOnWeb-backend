package es.ficonlan.web.backend.util;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import es.ficonlan.web.backend.jersey.util.JsonRegistrationStateDeserializer;
import es.ficonlan.web.backend.jersey.util.JsonRegistrationStateSerializer;
import es.ficonlan.web.backend.model.registration.Registration.RegistrationState;

public class EventRegistrationState {

	private boolean open;
	
	private RegistrationState state;

	public EventRegistrationState(boolean open, RegistrationState state) {
		super();
		this.open = open;
		this.state = state;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@JsonDeserialize(using = JsonRegistrationStateDeserializer.class)
	@JsonSerialize(using = JsonRegistrationStateSerializer.class)
	public RegistrationState getState() {
		return state;
	}

	public void setState(RegistrationState state) {
		this.state = state;
	}
	
}
