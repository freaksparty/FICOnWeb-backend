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
								Thread.sleep(30000);
							}
							Thread.sleep(100);
						}
						else {
							//System.out.println("No hay Emails");
							Thread.sleep(3000);
						}
					}
				}
				catch (Exception e2) {
					System.out.println("Error en el sistema de Emvío de Email");
					throw new RuntimeException("EmailFIFO thread error: " + e2.getMessage());
				}
			}
		};
		mailFIFO.start();
	}

	public static int mailQueueSize() {
		return emails.size();
	}
	
	public static void adEmailToQueue(Email email) {
		emails.add(email);
	}
}
