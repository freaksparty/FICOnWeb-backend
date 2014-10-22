package es.ficonlan.web.backend.model.emailservice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ficonlan.web.backend.model.email.Email;
import es.ficonlan.web.backend.model.emailadress.Adress;

/**
 * @author Miguel Ángel Castillo Bellagona
 */
@Service("EmailService")
@Transactional
public class EmailServiceImpl implements EmailService {

	@Transactional
	@Override
	public List<Adress> getAllAdress(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Adress addAdress(String sessionId, Adress adress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Adress modifyAdress(String serssionId, int adressId, Adress newAdress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void deleteAdress(String serssionId, int adressId) {
		// TODO Auto-generated method stub

	}
	
	@Transactional
	@Override
	public List<Email> getAllMails(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<Email> getConfirmedMails(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<Email> getNoConfirmedMails(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Email getEmail(String sessionId, int emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Email addEmail(String sessionId, Email email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public Email modifyEmail(String serssionId, int emailId, Email newEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void deleteEmail(String serssionId, int emailId) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public Email sendEmail(String sessionId, int emailId) {
		// TODO Auto-generated method stub
		return null;
	}

}
