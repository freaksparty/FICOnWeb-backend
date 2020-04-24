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

package es.ficonlan.web.backend.model.email;

/*
* @author Miguel Ángel Castillo Bellagona
* @version 2.1
*/
public class SendMailThread extends Thread {
	
//	private Email email;
//	
//	public SendMailThread(Email email) {
//		this.email = email;
//	}
//	
//	public Email getEmail() {
//		return email;
//	}
//
//	public void setEmail(Email email) {
//		this.email = email;
//	}
//
//	public boolean sendMail() throws ServiceException {
//
//		try {
//			Properties props = new Properties();
//			props.setProperty("mail.smtp.host", "smtp.gmail.com");
//			props.setProperty("mail.smtp.starttls.enable", "true");
//			props.setProperty("mail.smtp.port", "587");
////			props.setProperty("mail.smtp.ssl.enable", "true");
////			props.setProperty("mail.smtp.port", "465");
//			props.setProperty("mail.smtp.ssl.enable", "true");
//			props.setProperty("mail.smtp.port", "465");
//			props.setProperty("mail.smtp.socketFactory.port", "465");
//			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.setProperty("mail.smtp.user", email.userSend);
//			props.setProperty("mail.smtp.auth", "true");
//
//
//			Authenticator auth = new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(email.userSend, email.passSend);
//				}
//			};
//			
//			Session session = Session.getDefaultInstance(props, auth);
//			BodyPart texto = new MimeBodyPart();
//			texto.setText(email.getMensaje());
//
//			BodyPart adjunto = new MimeBodyPart();
//			if(email.getRutaArchivo()!=null) if (!email.getRutaArchivo().equals("")) {
//				adjunto.setDataHandler(new DataHandler(new FileDataSource(email.getRutaArchivo())));
//				adjunto.setFileName(email.getNombreArchivo());
//			}
//
//			MimeMultipart multiParte = new MimeMultipart();
//			multiParte.addBodyPart(texto);
//			if(email.getRutaArchivo()!=null) if (!email.getRutaArchivo().equals("")) {
//				multiParte.addBodyPart(adjunto);
//			}
//
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(email.userSend));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getDestinatario()));
//			message.setSubject(email.getAsunto());
//			message.setContent(multiParte);
//
//			//Transport t = session.getTransport("smtp");
//			//t.connect(email.userSend,email.passSend);
//			//t.sendMessage(message, message.getAllRecipients());
//			//t.close();
//			Transport.send(message);
//
//			email.setSendDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
//			
//			return true;
//		} 
//		catch (MessagingException e) 
//		{
//			e.printStackTrace();
//
//			email.setSendDate(null);
//			
//			return false;
//		}
//
//	}

	@Override
	public void run()
	{
//		try {
//			this.sendMail();
//		}
//		catch (ServiceException e) {
//			
//		}
	}
}