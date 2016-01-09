package es.ficonlan.web.backend.model.email;

import java.util.LinkedList;
import java.util.Queue;

import es.ficonlan.web.backend.entities.Email;

public class EmailFIFO {

	public static Queue<Email>	emails	= new LinkedList<Email>();

	public static void startEmailQueueThread() {
		Thread mailFIFO = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					while (true) {
						//System.out.println("DENTRO EMAIL THREAD");
						Email email = emails.poll();
						if (email != null) {
							try {
								email.sendMail();
								//System.out.println("Email Enviado " + email.getAsunto());
							}
							catch (Exception e1) {
								emails.add(email);
								//System.out.println("Error al enviar el Email " + email.getAsunto());
								System.err.println("Could not send email.");
								e1.printStackTrace();
								Thread.sleep(15*60*1000);
							}
							Thread.sleep(10*1000);
						}
						else {
							//System.out.println("No hay Emails");
							Thread.sleep(60*1000);
						}
					}
				}
				catch (Exception e2) {
					System.out.println("Error en el sistema de Envío de Email");
					e2.printStackTrace();
					throw new RuntimeException("EmailFIFO thread error: " + e2.getMessage());
				}
			}
		};
		System.out.println("INFO: starting email thread");
		mailFIFO.start();
	}

	public static int mailQueueSize() {
		return emails.size();
	}
	
	public static void addEmailToQueue(Email email) {
		emails.add(email);
	}
}
